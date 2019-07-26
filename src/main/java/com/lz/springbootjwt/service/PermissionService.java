package com.lz.springbootjwt.service;

import java.util.List;

/**
 * @author lzj
 * @create 2019-07-26 18:38
 */
public interface PermissionService {
    List<String> findPermissionUrl();

    List<String> findPermissionUrl(List<Integer> roleIds);

}
