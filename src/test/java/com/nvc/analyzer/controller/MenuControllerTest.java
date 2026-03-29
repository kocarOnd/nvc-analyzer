package com.nvc.analyzer.controller;

import com.nvc.analyzer.App;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Method;

import static org.mockito.Mockito.times;

class MenuControllerTest {

    private MenuController menuController;
    private MockedStatic<App> mockedApp;

    @BeforeEach
    void setUp() {
        menuController = new MenuController();
        mockedApp = Mockito.mockStatic(App.class);
    }

    @AfterEach
    void tearDown() {
        mockedApp.close();
    }

    @Test
    void testSwitchToCreate_ChangesRootToAnalysisView() throws Exception {
        Method switchToCreateMethod = MenuController.class.getDeclaredMethod("switchToCreate");
        switchToCreateMethod.setAccessible(true);
        switchToCreateMethod.invoke(menuController);

        mockedApp.verify(() -> App.setRoot("analysis_view"), times(1));
    }

    @Test
    void testSwitchToBrowse_ChangesRootToHistoryView() throws Exception {
        Method switchToBrowseMethod = MenuController.class.getDeclaredMethod("switchToBrowse");
        switchToBrowseMethod.setAccessible(true);
        switchToBrowseMethod.invoke(menuController);

        mockedApp.verify(() -> App.setRoot("history_view"), times(1));
    }
}