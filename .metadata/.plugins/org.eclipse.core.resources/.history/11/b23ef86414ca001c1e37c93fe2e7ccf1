package com.intuit.accountant.services.dcm.services;

import com.intuit.accountant.services.common.iam.IamAuthContext;
import com.intuit.accountant.services.dcm.model.DocumentResponse;

/**
 * Created by sshashidhar on 16/05/19.
 */
public interface IDocumentService {

    DocumentResponse uploadDocument(String realmId, String tid);

    DocumentResponse downloadDocument(String realmId, String tid, String docId);

    boolean deleteDocument(String realmId, String tid, String docId);

    boolean isComponentUp();
}
