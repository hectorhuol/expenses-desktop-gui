package com.financial.analisys.financial_gui;

import org.junit.Assert;
import org.junit.Test;

import com.financial.analisys.expenser.desktop.gui.util.GUIUtils;


public class GUIUtilsTest {
	
	@Test
	public void testIsANumberUtility() {		
		Assert.assertEquals(true, GUIUtils.isANumber("5"));
		Assert.assertEquals(true, GUIUtils.isANumber("-5"));
		Assert.assertEquals(true, GUIUtils.isANumber("-55"));
		Assert.assertEquals(true, GUIUtils.isANumber("-5.0"));
		Assert.assertEquals(true, GUIUtils.isANumber("-55.01223"));
		Assert.assertEquals(false, GUIUtils.isANumber("--55.01223"));
		Assert.assertEquals(false, GUIUtils.isANumber("--5"));
		Assert.assertEquals(false, GUIUtils.isANumber("a"));
		Assert.assertEquals(false, GUIUtils.isANumber("abc"));
		Assert.assertEquals(false, GUIUtils.isANumber("-a"));
	}

}
