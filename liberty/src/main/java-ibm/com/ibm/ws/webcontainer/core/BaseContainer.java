package com.ibm.ws.webcontainer.core;

import com.ibm.ws.container.AbstractContainer;
import com.ibm.wsspi.webcontainer.RequestProcessor;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class BaseContainer extends AbstractContainer implements RequestProcessor {



    public void handleRequest(ServletRequest req, ServletResponse res) throws Exception
    {
        throw new Exception("Super class implementation called...Subclass must override this method.");
    }


    public boolean isInternal() {
        // TODO Auto-generated method stub
        return false;
    }

    public String getName(){
        return "";
    }



}
