package com.zycx.system.sys.controller;


import com.zycx.system.common.base.constant.SystemStaticConst;
import com.zycx.system.common.base.controller.GenericController;
import com.zycx.system.common.base.service.GenericService;
import com.zycx.system.sys.entity.QueryUserRole;
import com.zycx.system.sys.entity.Tree;
import com.zycx.system.sys.entity.UserRole;
import com.zycx.system.sys.mapper.TreeMapper;
import com.zycx.system.sys.service.TreeService;
import com.zycx.system.sys.service.UserRoleService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 类描述：
* @auther linzf
* @create 2017/10/10 0010 
*/
@Controller
@RequestMapping("/role")
public class RoleController extends GenericController<UserRole,QueryUserRole> {

    @Inject
    private UserRoleService userRoleService;

    @Inject
    private TreeService treeService;

    @Inject
    private TreeMapper treeMapper;

    @Override
    protected GenericService<UserRole, QueryUserRole> getService() {
        return userRoleService;
    }

    /**
     * 功能描述：根据用户的权限去加载角色数据
     * @return
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/loadRoleTree",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> loadRoleTree(UserRole entity){
        entity = userRoleService.getUserRoleAssociate(entity);
        List<Tree> treeList = treeService.query(null);
        if(entity!=null){
            for(Tree tree:entity.getTreeList()){
                treeList.stream().forEach(t->{
                    if(t.getId()==tree.getId()){
                        t.setChecked(true);
                    }
                });
            }
        }
        Map<String,Object> result = new HashMap<String, Object>();
        result.put(SystemStaticConst.RESULT, SystemStaticConst.SUCCESS);
        result.put("data",treeMapper.treesToTressDTOs(treeList));
        return result;
    }

}
