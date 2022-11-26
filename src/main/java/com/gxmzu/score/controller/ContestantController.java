package com.gxmzu.score.controller;

import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.domain.entity.Contestant;
import com.gxmzu.score.service.IContestantService;
import com.gxmzu.score.service.TokenService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10/9:58
 * @Description: 参赛者接口
 */
@RestController
@RequestMapping("/contestant")
public class ContestantController extends BaseController {

    @Resource
    private IContestantService contestantService;

    @Resource
    private TokenService tokenService;

    /**
     * 获取参赛者列表
     *
     * @param contestant 参赛者查询信息
     * @return 参赛者列表
     */
    @GetMapping("/list")
    public AjaxResult getContestantList(Contestant contestant) {
        startPage(contestant);
        List<Contestant> list = contestantService.getContestantList(contestant);
        return getDataTable(list);
    }

}
