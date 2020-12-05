package com.lp.rubbishui.manager;


import com.lp.rubbishui.net.RetrofitServiceManager;
import com.lp.rubbishui.net.UploadRetrofitManager;
import com.lp.rubbishui.net.service.RubbishService;
import com.lp.rubbishui.net.service.UploadService;

public class RubbishManager {

    public static RubbishManager getInstance() {
        return manager;
    }
    public static RubbishManager manager = new RubbishManager();
    private RubbishService service;
    private UploadService uploadService;
    private RubbishManager() {
        service = RetrofitServiceManager.getInstance().create(RubbishService.class);
        uploadService = UploadRetrofitManager.getInstance().create(UploadService.class);
    }
    public RubbishService getService(){
        return service;
    }

    public UploadService getUploadService(){
        return uploadService;
    }
}
