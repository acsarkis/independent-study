package com.mvc.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.mvc.view.subviews.EditorPanel;

public class ButtonControlView extends JPanel implements ActionListener {
	private int width;
	private int height;
	
	private final String runClassifierButtonText = "Run";
	private JButton runClassifierButton;
	private final EditorPanel editorPanel;
	
	public ButtonControlView(int height, EditorPanel editorPanel) {
		this.editorPanel = editorPanel;
		this.setLayout(new FlowLayout());
		this.width = 190;
		this.height = height;
		
		this.setBackground(Color.RED);
		
		this.createButtons();
	}
	
	private void createButtons() {
		this.runClassifierButton = this.registerButton(this.runClassifierButton, this.runClassifierButtonText);
	}
	
	private JButton registerButton(JButton button, String text) {
		button = new JButton(text);
		button.addActionListener(this);
		this.add(button);
		
		return button;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == this.runClassifierButton) {
			this.editorPanel.runSelectedParameters();
		}
	}
}
