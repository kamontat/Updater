package com.kamontat.examples;

import com.kamontat.constants.SizeUnit;
import com.kamontat.exception.UpdateException;
import com.kamontat.factory.UpdaterFactory;
import com.kamontat.implementation.GUpdater;
import com.kamontat.objects.Owner;
import com.kamontat.rawapi.Updatable;
import com.kamontat.utilities.SizeUtil;

/**
 * @author kamontat
 * @version 1.0
 * @since Tue 21/Mar/2017 - 9:54 AM
 */
public class TestImp {
    public static void setFactory(Updatable updatable) {
        UpdaterFactory.setUpdater(updatable);
    }
    
    public static void main(String[] args) throws UpdateException {
        Owner owner = new Owner("kamontat", "CheckIDNumber");
        Updatable update = new GUpdater(owner, "v1.0.0", 1);
        setFactory(update);
        
        UpdaterFactory factory = UpdaterFactory.getFactory();
        factory.checkRelease();
        
        System.out.println(factory.getTitle());
        System.out.println(factory.getDescription());
        System.out.println("current: " + factory.getCurrentVersion());
        System.out.println("remote: " + factory.getRemoteVersion());
        if (!factory.isLatest()) {
            System.out.println(factory.getDownloadFileName());
            
            // decode size example
            long size = factory.getDownloadSize();
            double mb = SizeUtil.getSize(size, SizeUnit.B).convertTo(SizeUnit.MB).getSize().doubleValue();
            System.out.printf("size: %.2f MB \n", mb);
            
            System.out.println(factory.getDownloadType());
            
            /* download with default */
            // System.out.println(factory.download(Downloadable.getDefaultAction(update.getDownload().getReader())));
            
            /* download with no action */
            // System.out.println(factory.download(null));
            
            /* download with custom action */
            // System.out.println(factory.download(() -> {
            // 	   System.out.println("Loading: " + update.getDownload().getReader().getBytesRead() + "/" + update.getDownload().getReader().getTotalByte());
            // }));
        }
    }
}
