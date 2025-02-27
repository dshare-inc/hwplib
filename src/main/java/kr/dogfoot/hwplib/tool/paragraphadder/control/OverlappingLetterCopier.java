package kr.dogfoot.hwplib.tool.paragraphadder.control;


import kr.dogfoot.hwplib.object.bodytext.control.ControlOverlappingLetter;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.CtrlHeaderOverlappingLetter;
import kr.dogfoot.hwplib.object.etc.HWPString;
import kr.dogfoot.hwplib.tool.paragraphadder.docinfo.DocInfoAdder;

public class OverlappingLetterCopier {
    public static void copy(ControlOverlappingLetter source, ControlOverlappingLetter target, DocInfoAdder docInfoAdder) {
        header(source.getHeader(), target.getHeader(), docInfoAdder);
        if (source.getCtrlData() != null) {
            target.createCtrlData();
            target.getCtrlData().copy(source.getCtrlData());
        } else {
            target.deleteCtrlData();
        }
    }

    private static void header(CtrlHeaderOverlappingLetter source, CtrlHeaderOverlappingLetter target, DocInfoAdder docInfoAdder) {
        target.setBorderType(source.getBorderType());
        target.setExpendInsideLetter(source.getExpendInsideLetter());
        target.setInternalFontSize(source.getInternalFontSize());

        for(HWPString s : source.getOverlappingLetterList()) {
            target.addOverlappingLetter(s.clone());
        }

        for(Long charShapeId : source.getCharShapeIdList()) {
            target.addCharShapeId(docInfoAdder.forCharShape().processById(charShapeId.intValue()));
        }
    }
}