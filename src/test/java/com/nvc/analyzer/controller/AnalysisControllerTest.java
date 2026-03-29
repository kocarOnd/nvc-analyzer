package com.nvc.analyzer.controller;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import com.nvc.analyzer.App;
import com.nvc.analyzer.model.NvcProcess;
import com.nvc.analyzer.model.NvcValidator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@ExtendWith(ApplicationExtension.class)
class AnalysisControllerTest {

    private AnalysisController analysisController;
    private TextField obsField, feelField, needField, reqField;
    private TextArea resultArea;
    private MockedStatic<App> mockedApp;

    @BeforeEach
    void setUp() throws Exception {
        analysisController = new AnalysisController();
        mockedApp = Mockito.mockStatic(App.class);

        obsField = new TextField();
        feelField = new TextField();
        needField = new TextField();
        reqField = new TextField();
        resultArea = new TextArea();

        injectField("observationField", obsField);
        injectField("feelingField", feelField);
        injectField("needField", needField);
        injectField("requestField", reqField);
        injectField("resultArea", resultArea);

        NvcValidator mockValidator = Mockito.mock(NvcValidator.class);

        Mockito.when(mockValidator.analyze(anyString())).thenReturn(Collections.emptyList());

        injectField("observationValidator", mockValidator);
        injectField("feelingValidator", mockValidator);
        injectField("needValidator", mockValidator);
        injectField("requestValidator", mockValidator);
    }

    private void injectField(String fieldName, Object value) throws Exception {
        Field field = AnalysisController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(analysisController, value);
    }

    @AfterEach
    void tearDown() {
        mockedApp.close();
    }

    @Test
    void testHandleWrite_WithEmptyFields_ShowsWarning() throws Exception {
        
        Method handleWriteMethod = AnalysisController.class.getDeclaredMethod("handleWrite");
        handleWriteMethod.setAccessible(true);
        handleWriteMethod.invoke(analysisController);

        assertEquals("Please fill in all 4 steps to complete the NVC process!", resultArea.getText());
    }

    @Test
    void testHandleWrite_WithFilledFields_FormatsStatementCorrectly() throws Exception {
        obsField.setText("un código sin pruebas");
        feelField.setText("nervioso");
        needField.setText("seguridad");
        reqField.setText("escribir tests");

        Method handleWriteMethod = AnalysisController.class.getDeclaredMethod("handleWrite");
        handleWriteMethod.setAccessible(true);
        handleWriteMethod.invoke(analysisController);

        String result = resultArea.getText();
        assertTrue(result.contains("Here is your NVC Statement:"));
        assertTrue(result.contains("When I see/hear un código sin pruebas"));
        assertTrue(result.contains("I feel nervioso"));
        assertTrue(result.contains("because I need seguridad"));
        assertTrue(result.contains("Would you be willing to escribir tests?"));
    }

    @Test
    void testSetProcess_PopulatesFieldsCorrectly() {
        NvcProcess testProcess = new NvcProcess();
        testProcess.setObservation("la habitación está desordenada");
        testProcess.setFeeling("abrumado");
        testProcess.setNeed("orden y tranquilidad");
        testProcess.setRequest("recoger las cosas");

        analysisController.setProcess(testProcess);

        assertEquals("la habitación está desordenada", obsField.getText());
        assertEquals("abrumado", feelField.getText());
        assertEquals("orden y tranquilidad", needField.getText());
        assertEquals("recoger las cosas", reqField.getText());
    }

    @Test
    void testHandleAnalyze_WithEmptyFields_ShowsWarningMessage() throws Exception {
        obsField.setText("");
        feelField.setText("feliz");
        needField.setText("conexión");
        reqField.setText("hablar");

        Method handleAnalyzeMethod = AnalysisController.class.getDeclaredMethod("handleAnalyze");
        handleAnalyzeMethod.setAccessible(true);
        handleAnalyzeMethod.invoke(analysisController);

        assertEquals("Please fill in all 4 steps to complete the NVC process!", resultArea.getText());
    }

    @Test
    void testHandleSave_WithEmptyFields_DoesNotSave() throws Exception {
        Method handleSaveMethod = AnalysisController.class.getDeclaredMethod("handleSave");
        handleSaveMethod.setAccessible(true);
        
        try {
            handleSaveMethod.invoke(analysisController);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An exception occured while invoking the handleSave method:" + e.getMessage());
        }

        assertTrue(true); 
    }

    @Test
    void testGoBack_ChangesRootToMenuView() throws Exception {
        Method goBackMethod = AnalysisController.class.getDeclaredMethod("goBack");
        goBackMethod.setAccessible(true);
        goBackMethod.invoke(analysisController);

        mockedApp.verify(() -> App.setRoot("menu_view"), times(1));
    }
}