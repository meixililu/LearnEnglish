package com.messi.learnenglish.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "SYMBOL_LIST_DAO".
 */
public class SymbolListDao {

    private Long id;
    private String SDName;
    private String SDCode;
    private String SDDes;
    private String SDInfo;
    private String SDAudioMp3Url;
    private String SDTeacherMp3Url;
    private String SDAudioMp3FilePath;
    private String SDTeacherMp3FilePath;
    private String backup1;
    private String backup2;
    private String backup3;

    public SymbolListDao() {
    }

    public SymbolListDao(Long id) {
        this.id = id;
    }

    public SymbolListDao(Long id, String SDName, String SDCode, String SDDes, String SDInfo, String SDAudioMp3Url, String SDTeacherMp3Url, String SDAudioMp3FilePath, String SDTeacherMp3FilePath, String backup1, String backup2, String backup3) {
        this.id = id;
        this.SDName = SDName;
        this.SDCode = SDCode;
        this.SDDes = SDDes;
        this.SDInfo = SDInfo;
        this.SDAudioMp3Url = SDAudioMp3Url;
        this.SDTeacherMp3Url = SDTeacherMp3Url;
        this.SDAudioMp3FilePath = SDAudioMp3FilePath;
        this.SDTeacherMp3FilePath = SDTeacherMp3FilePath;
        this.backup1 = backup1;
        this.backup2 = backup2;
        this.backup3 = backup3;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSDName() {
        return SDName;
    }

    public void setSDName(String SDName) {
        this.SDName = SDName;
    }

    public String getSDCode() {
        return SDCode;
    }

    public void setSDCode(String SDCode) {
        this.SDCode = SDCode;
    }

    public String getSDDes() {
        return SDDes;
    }

    public void setSDDes(String SDDes) {
        this.SDDes = SDDes;
    }

    public String getSDInfo() {
        return SDInfo;
    }

    public void setSDInfo(String SDInfo) {
        this.SDInfo = SDInfo;
    }

    public String getSDAudioMp3Url() {
        return SDAudioMp3Url;
    }

    public void setSDAudioMp3Url(String SDAudioMp3Url) {
        this.SDAudioMp3Url = SDAudioMp3Url;
    }

    public String getSDTeacherMp3Url() {
        return SDTeacherMp3Url;
    }

    public void setSDTeacherMp3Url(String SDTeacherMp3Url) {
        this.SDTeacherMp3Url = SDTeacherMp3Url;
    }

    public String getSDAudioMp3FilePath() {
        return SDAudioMp3FilePath;
    }

    public void setSDAudioMp3FilePath(String SDAudioMp3FilePath) {
        this.SDAudioMp3FilePath = SDAudioMp3FilePath;
    }

    public String getSDTeacherMp3FilePath() {
        return SDTeacherMp3FilePath;
    }

    public void setSDTeacherMp3FilePath(String SDTeacherMp3FilePath) {
        this.SDTeacherMp3FilePath = SDTeacherMp3FilePath;
    }

    public String getBackup1() {
        return backup1;
    }

    public void setBackup1(String backup1) {
        this.backup1 = backup1;
    }

    public String getBackup2() {
        return backup2;
    }

    public void setBackup2(String backup2) {
        this.backup2 = backup2;
    }

    public String getBackup3() {
        return backup3;
    }

    public void setBackup3(String backup3) {
        this.backup3 = backup3;
    }

}