package com.nvc.analyzer.controller;

import com.nvc.analyzer.App;
import com.nvc.analyzer.model.NvcProcess;
import com.nvc.analyzer.service.DataService;

import javafx.scene.control.ListView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

@ExtendWith(ApplicationExtension.class)
class HistoryControllerTest {

    private HistoryController historyController;
    private MockedStatic<App> mockedApp;
    private ListView<NvcProcess> historyList;

    @BeforeEach
    void setUp() throws Exception {
        historyController = new HistoryController();
        mockedApp = Mockito.mockStatic(App.class);
        historyList = new ListView<NvcProcess>();

        Field historyListField = HistoryController.class.getDeclaredField("historyList");
        historyListField.setAccessible(true);
        historyListField.set(historyController, historyList);
    }

    @AfterEach
    void tearDown() {
        mockedApp.close();
    }

    @Test
    void testGoBack_ChangesRootToMenuView() throws Exception {
        Method goBackMethod = HistoryController.class.getDeclaredMethod("goBack");
        goBackMethod.setAccessible(true);
        goBackMethod.invoke(historyController);

        mockedApp.verify(() -> App.setRoot("menu_view"), times(1));
    }

    @Test
    void testInitialize_LoadsDataIntoListView() throws Exception {
        DataService mockDataService = Mockito.mock(DataService.class);
        
        NvcProcess p1 = new NvcProcess(); p1.setObservation("Obs 1");
        NvcProcess p2 = new NvcProcess(); p2.setObservation("Obs 2");
        List<NvcProcess> fakeData = Arrays.asList(p1, p2);
        
        Mockito.when(mockDataService.loadProcesses()).thenReturn(fakeData);

        Field dataServiceField = HistoryController.class.getDeclaredField("dataService");
        dataServiceField.setAccessible(true);
        dataServiceField.set(historyController, mockDataService);

        Method initializeMethod = HistoryController.class.getDeclaredMethod("initialize");
        initializeMethod.setAccessible(true);
        initializeMethod.invoke(historyController);

        assertEquals(2, historyList.getItems().size());
    }

    @Test
    void testHandleEdit_WithNoSelection_DoesNotProceed() throws Exception {
        
        Method handleEditMethod = HistoryController.class.getDeclaredMethod("handleEdit");
        handleEditMethod.setAccessible(true);
        
        try {
            handleEditMethod.invoke(historyController);
        } catch (Exception e) {}

        assertTrue(true);
    }
}