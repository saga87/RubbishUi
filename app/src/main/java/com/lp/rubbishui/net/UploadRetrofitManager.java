package com.lp.rubbishui.net;



import com.lp.rubbishui.net.fastjson.FastJsonConverterFactory;

import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class UploadRetrofitManager {
    private static final int DEFAULT_TIME_OUT = 60*5;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 20*10;
    private Retrofit mRetrofit;

    private UploadRetrofitManager() {
        // 创建 OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);
        builder.sslSocketFactory(createEasySSLContext().getSocketFactory(),new MyTrustManager())
                .hostnameVerifier(new MyHostnameVerifier());

        // 创建Retrofit
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .baseUrl(RetrofitServiceManager.getUrl())
                .build();
    }


    private static class SingletonHolder {
        private static final UploadRetrofitManager INSTANCE = new UploadRetrofitManager();
    }

    /**
     * 获取UploadRetrofitManager
     *
     * @return
     */
    public static UploadRetrofitManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取对应的Service
     *
     * @param service Service 的 class
     * @param <T>
     */
    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }



    private static class MyHostnameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static class MyTrustManager implements X509TrustManager {

        @Override
        public X509Certificate[] getAcceptedIssuers() {

            return new X509Certificate[0];
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws java.security.cert.CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws java.security.cert.CertificateException {

        }
    }


    private static SSLContext createEasySSLContext() {
        try {
            SSLContext context = SSLContext.getInstance("SSL");
            context.init(
                    null,
                    new TrustManager[]{new MyTrustManager()},
                    null);
            return context;
        } catch (Exception e) {
            return null;
        }
    }
}
