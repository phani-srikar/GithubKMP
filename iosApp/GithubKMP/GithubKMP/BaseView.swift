//
//  BaseView.swift
//  GithubKMP
//
//  Created by Edupuganti, Phani Srikar on 4/6/20.
//  Copyright © 2020 Edupuganti Phani Srikar. All rights reserved.
//

import Foundation
import shared
import UIKit

extension UIViewController : BaseView {
    
    public func showError(error: KotlinThrowable) {
        let title: String = "Error"
        var errorMessage: String? = nil
        
        switch error {
        case is UpdateProblem:
            errorMessage = "Failed to get Data From Server"
        default:
            errorMessage = "Unknown Error"
        }
        
        if let message = errorMessage {
            showError(title: title, message: message)
        }
    }
    
    func showError(title: String, message: String) {
        let alert = UIAlertController(title: title,
                                      message: message,
                                      preferredStyle: UIAlertController.Style.alert)
        alert.addAction(UIAlertAction(title: "Dismiss",
                                      style: UIAlertAction.Style.default,
                                      handler: nil))
        self.present(alert, animated: true, completion: nil)
    }
}
