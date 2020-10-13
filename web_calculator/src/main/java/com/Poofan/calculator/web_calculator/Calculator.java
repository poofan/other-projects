package com.Poofan.calculator.web_calculator;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Calculator {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				CalculatorFrame frame = new CalculatorFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
	
}

//создаём фрейм с дефолтным размером окна
class CalculatorFrame extends JFrame {
	private static final int DEFAULT_WIDTH = 800;//ширина по умолчанию
	private static final int DEFAULT_HEIGHT = 800;//высота по умолчанию
	
	public CalculatorFrame() {
		setTitle("Калькулятор");//наименование окна/фрейма
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);//устанавливаем размер фрейма
		CalculatorPanel panel = new CalculatorPanel();//объект панели фрейма
		add(panel);//добавляем панель
		pack();//пакуем фрейм
	}
}

class CalculatorPanel extends JPanel {
	private Boolean start;
	private JButton display;
	private String lastCommand;
	private float result;
	private JPanel panel;
	
	public CalculatorPanel() {
		setLayout(new BorderLayout());
		
		result = 0;
		lastCommand = "=";
		start = true;
		
		display = new JButton("0");
		display.setEnabled(false);
		add(display, BorderLayout.NORTH);
		
		ActionListener input = new InputAction();
		ActionListener command = new CommandAction();
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(4, 4));
		
		addButton("7", input);
		addButton("8", input);
		addButton("9", input);
		addButton("/", command);
		
		addButton("4", input);
		addButton("5", input);
		addButton("6", input);
		addButton("*", command);
		
		addButton("1", input);
		addButton("2", input);
		addButton("3", input);
		addButton("-", command);
		
		addButton("0", input);
		addButton(".", input);
		addButton("+", command);
		addButton("=", command);
		
		add(panel, BorderLayout.CENTER);
	}
	
	//метод, добавляющий кнопки в панель
	private void addButton(String inputLabel, ActionListener listener) {//1-й параметр принимает символ, 2-ой параметр - устанавливает команду для символа
		JButton button = new JButton(inputLabel);//создаём кнопку
		button.addActionListener(listener);//передаём кнопке команду
		panel.add(button);//добавляем кнопку на панель
	}
	
	private class InputAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String input = arg0.getActionCommand();
			if(start) {
				display.setText("");
				start = false;
			}
			display.setText(display.getText() + input);
		}		
	}
	
	//создаём класс, который содержит в себе метод присваивания команды будущей кнопке
	private class CommandAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//переменная, которая будет хранить передаваемую команду
			String command = e.getActionCommand();
			if(start) {
				if(command.equals("-")) {
					display.setText(command);
					start = false;
				} else lastCommand = command;
			} else {
				calculate(Float.parseFloat(display.getText()));
				lastCommand = command;
				start = true;
			}
		}		
	}
	
	//метод, выполняющий определённые функции
	public void calculate(float x) {
		switch(lastCommand) {
		case "+":
			result += Math.round(x);
			break;
		case "-":
			result -= Math.round(x);
			break;
		case "/": 
			result /= Math.round(x);
			break;
		case "*":
			result *= Math.round(x);
			break;
		case "=": 
			result = Math.round(x);
			break;
		}
		
		display.setText("" + result);
	}
}

