package com.workmotion.hr.workmotionhr.model;

import java.util.ArrayList;
import java.util.List;

public enum EmployeeState {
    ADDED{
       public boolean isNextStateValid(EmployeeState state){
            return state.equals(IN_CHECK);
        }
    },IN_CHECK{
        public boolean isNextStateValid(EmployeeState state) {
            return state.equals(APPROVED);
        }
    },APPROVED{
        public boolean isNextStateValid(EmployeeState state) {
            return state.equals(ACTIVE) || state.equals(IN_CHECK);
        }
    },ACTIVE {
        public boolean isNextStateValid(EmployeeState state) {
                return false;
        }
    }/* , SECURITY_CHECK_STARTED{
        public boolean isNextStateValid(EmployeeState state) {
            List<EmployeeState> allowedStateList = new ArrayList<>();
            allowedStateList.add(SECURITY_CHECK_FINISHED);
            allowedStateList.add(WORK_PERMIT_CHECK_STARTED);
            allowedStateList.add(WORK_PERMIT_CHECK_PENDING_VERIFICATION);
            allowedStateList.add(WORK_PERMIT_CHECK_FINISHED);
            return allowedStateList.contains(state);
          }
        },
    SECURITY_CHECK_FINISHED{
        public boolean isNextStateValid(EmployeeState state) {
            List<EmployeeState> allowedStateList = new ArrayList<>();
            allowedStateList.add(WORK_PERMIT_CHECK_STARTED);
            allowedStateList.add(WORK_PERMIT_CHECK_PENDING_VERIFICATION);
            allowedStateList.add(WORK_PERMIT_CHECK_FINISHED);
            allowedStateList.add(APPROVED);
            return allowedStateList.contains(state);
        }
    } ,WORK_PERMIT_CHECK_STARTED{
        public boolean isNextStateValid(EmployeeState state){
            List<EmployeeState> allowedStateList = new ArrayList<>();
            allowedStateList.add(SECURITY_CHECK_STARTED);
            allowedStateList.add(SECURITY_CHECK_FINISHED);
            allowedStateList.add(WORK_PERMIT_CHECK_PENDING_VERIFICATION);
            allowedStateList.add(WORK_PERMIT_CHECK_FINISHED);
            return allowedStateList.contains(state);
        }
    } , WORK_PERMIT_CHECK_PENDING_VERIFICATION{
        public boolean isNextStateValid(EmployeeState state){
            List<EmployeeState> allowedStateList = new ArrayList<>();
            allowedStateList.add(SECURITY_CHECK_STARTED);
            allowedStateList.add(SECURITY_CHECK_FINISHED);
            allowedStateList.add(WORK_PERMIT_CHECK_PENDING_VERIFICATION);
            allowedStateList.add(WORK_PERMIT_CHECK_FINISHED);
            return allowedStateList.contains(state);
        }
    },
    WORK_PERMIT_CHECK_FINISHED{
        public boolean isNextStateValid(EmployeeState state){
            List<EmployeeState> allowedStateList = new ArrayList<>();
            allowedStateList.add(SECURITY_CHECK_STARTED);
            allowedStateList.add(SECURITY_CHECK_FINISHED);
            allowedStateList.add(APPROVED);
            return allowedStateList.contains(state);
        }
    }*/;

    public abstract boolean isNextStateValid(EmployeeState state);
}
