package kubevisualizer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.border.Border;

import java.awt.Container;
import kubevisualizer.kubevisualizer;


public class kubevisualizer extends JFrame  {
	private static final int N = 8;

	String selectedPod = "";
	static String namespace="default";

	public kubevisualizer() {
		initComponents();
	}

	public static String[] fetchPods() throws IOException {
		Process process;
		String[] pods = new String[200];
		try {
			process = Runtime.getRuntime().exec(
					"cmd /c kubectl get pods -n "+namespace+" --no-headers -o custom-columns=\":metadata.name\"", null,
					new File("C:\\Users\\"));

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";

			int i = 0;
			while ((line = reader.readLine()) != null) {
				pods[i] = line;
				System.out.println(pods[i]);
				i++;

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pods;
	}
	
	public static String[] fetchNamespace() throws IOException {
		Process process;
		String[] namespace = new String[200];
		try {
			process = Runtime.getRuntime().exec(
					"cmd /c kubectl get namespace --no-headers -o custom-columns=\":metadata.name\"", null,
					new File("C:\\Users\\"));

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";

			int i = 0;
			while ((line = reader.readLine()) != null) {
				namespace[i] = line;
				System.out.println(namespace[i]);
				i++;

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return namespace;
	}

	public void describePods() throws IOException {
		Process process;
		String[] pods = new String[2000];
		System.out.println(selectedPod);
		try {
			process = Runtime.getRuntime().exec("cmd /c kubectl describe pods -n "+namespace+"  " + selectedPod, null,
					new File("C:\\Users\\"));

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";

			String lines = "";

			int i = 0;
			while ((line = reader.readLine()) != null) {
				lines = lines + line + "\n";
				i++;

			}

			System.out.println(lines);

			panelLast.removeAll();
			Border blackline = BorderFactory.createTitledBorder("Descirbe pods");
			panelLast.setBorder(blackline);
			JTextArea paneDescribe = new JTextArea(lines);
			panelLast.add(paneDescribe);
			panelLast.add(Box.createVerticalStrut(N));
			pack();
			repaint();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getLast1000Logs() throws IOException {
		Process process;
		String[] pods = new String[2000];
		System.out.println(selectedPod);
		try {
			process = Runtime.getRuntime().exec("cmd /c kubectl logs -n "+namespace+"  --tail 1000 " + selectedPod, null,
					new File("C:\\Users\\"));
			

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";

			String lines = "";

			int i = 0;
			while ((line = reader.readLine()) != null) {
				lines = lines + line + "\n";
				i++;

			}
			System.out.println(lines);
			
			if (lines == "") {
				reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				line="";
				lines="";
				i = 0;
				while ((line = reader.readLine()) != null) {
					lines = lines + line + "\n";
					i++;

				}
				System.out.println(lines);
			}
			
			if (lines.contains("choose one of")) {
			    int index = lines.indexOf("choose one of") + 16;
			    String containers = lines.substring(index);
			    String podContainer = containers.split(" ")[0];
			    System.out.println("voice pod container for "+selectedPod+" is "+podContainer);
			    process = Runtime.getRuntime().exec("cmd /c kubectl logs -n "+namespace+"  --tail 1000 " + selectedPod+" -c "+podContainer, null,
						new File("C:\\Users\\"));

				reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

				line="";
				lines="";
				i = 0;
				while ((line = reader.readLine()) != null) {
					lines = lines + line + "\n";
					i++;

				}
				
			}
			

			System.out.println(lines);

			panelLast.removeAll();
			Border blackline = BorderFactory.createTitledBorder("Get Last 1000 Log");
			panelLast.setBorder(blackline);
			JTextArea paneDescribe = new JTextArea(lines);
			panelLast.add(paneDescribe);
			panelLast.add(Box.createVerticalStrut(N));
			pack();
			repaint();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getLastCrashLogs() throws IOException {
		Process process;
		String[] pods = new String[2000];
		System.out.println(selectedPod);
		try {
			process = Runtime.getRuntime().exec("cmd /c kubectl logs --previous -n "+namespace+"  --tail 100 " + selectedPod, null,
					new File("C:\\Users\\"));
			

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";

			String lines = "";

			int i = 0;
			while ((line = reader.readLine()) != null) {
				lines = lines + line + "\n";
				i++;

			}
			System.out.println(lines);
			
			if (lines == "") {
				reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				line="";
				lines="";
				i = 0;
				while ((line = reader.readLine()) != null) {
					lines = lines + line + "\n";
					i++;

				}
				System.out.println(lines);
			}
			
			if (lines.contains("choose one of")) {
			    int index = lines.indexOf("choose one of") + 16;
			    String containers = lines.substring(index);
			    String podContainer = containers.split(" ")[0];
			    System.out.println("voice pod container for "+selectedPod+" is "+podContainer);
			    process = Runtime.getRuntime().exec("cmd /c kubectl logs --previous -n "+namespace+"  --tail 100 " + selectedPod+" -c "+podContainer, null,
						new File("C:\\Users\\"));

				reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

				line="";
				lines="";
				i = 0;
				while ((line = reader.readLine()) != null) {
					lines = lines + line + "\n";
					i++;

				}
				
			}
			

			System.out.println(lines);

			panelLast.removeAll();
			Border blackline = BorderFactory.createTitledBorder("Get Last Crash Log");
			panelLast.setBorder(blackline);
			JTextArea paneDescribe = new JTextArea(lines);
			panelLast.add(paneDescribe);
			panelLast.add(Box.createVerticalStrut(N));
			pack();
			repaint();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				new kubevisualizer().setVisible(true);
			}
		});
	}

	private JPanel panelCenter, panelCreating, panelLast,panelNameSpace,panelContent;
	private JScrollPane scrollPaneCreating, scrollPaneCenter, scrollPaneLast,scrollPaneNameSpace;

	private void initComponents() {

		String[] namepsace = {};
		try {
			namepsace = fetchNamespace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	    final JComboBox<String> cb = new JComboBox<String>(namepsace);
	    //setLayout(new GridLayout(2, 1));
	    cb.setVisible(true);
	    //add(cb);
		JButton btn = new JButton("OK");

		btn.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// code you write in here will be executed if the button was pressed
				String cbitem = cb.getSelectedItem().toString();
				System.out.println(cbitem);
				namespace = cbitem;
				EnablePods();
				//System.out.println(event);
			}
		});
		panelNameSpace = new JPanel();
		
		panelNameSpace.add(cb);
		panelNameSpace.add(btn);
		
		scrollPaneNameSpace= new JScrollPane(panelNameSpace, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    
		panelCreating = new JPanel();
		scrollPaneCreating = new JScrollPane(panelCreating, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		panelCenter = new JPanel();
		scrollPaneCenter = new JScrollPane(panelCenter);

		panelLast = new JPanel();
		scrollPaneLast = new JScrollPane(panelLast, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		// ----------------- Left Panel Init -----------------------
		panelCreating.setLayout(new BoxLayout(panelCreating, BoxLayout.Y_AXIS));
		panelCreating.setBorder(BorderFactory.createEmptyBorder(N, N, N, N));

		// ----------------- Center Panel Init -----------------------
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
		panelCenter.setBorder(BorderFactory.createEmptyBorder(N, N, N, N));

		// ----------------- Right Panel Init -----------------------
		panelLast.setLayout(new BoxLayout(panelLast, BoxLayout.Y_AXIS));
		panelLast.setBorder(BorderFactory.createEmptyBorder(N + 8, N + 8, N + 8, N + 8));

		EnablePods();

		add(scrollPaneNameSpace,BorderLayout.NORTH);
		
		panelContent =  new JPanel();
		panelContent.setLayout(new GridLayout(1, 3));
		// -------------------------------------------------------
		//setLayout(new GridLayout(1, 3));
		//add(cb);
		//add(btn);
		panelContent.add(scrollPaneCreating);
		panelContent.add(scrollPaneCenter);
		panelContent.add(scrollPaneLast);
		
		add(panelContent,BorderLayout.CENTER);
		
        /*Container contentPane = getContentPane();  
        SpringLayout layout = new SpringLayout();  
        contentPane.setLayout(layout);  
        
        contentPane.add(scrollPaneNameSpace);
        contentPane.add(scrollPaneCreating);
        contentPane.add(scrollPaneCenter);
        contentPane.add(scrollPaneLast);
        
        layout.putConstraint(SpringLayout.NORTH, scrollPaneNameSpace,6,SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.SOUTH, scrollPaneCreating,6,SpringLayout.SOUTH, contentPane);
        layout.putConstraint(SpringLayout.SOUTH, scrollPaneCenter,6,SpringLayout.SOUTH, contentPane);
        layout.putConstraint(SpringLayout.SOUTH, scrollPaneLast,6,SpringLayout.SOUTH, contentPane);
        layout.putConstraint(SpringLayout.SOUTH, scrollPaneCreating,1000,SpringLayout.SOUTH, scrollPaneNameSpace);
        layout.putConstraint(SpringLayout.SOUTH, scrollPaneCenter,1000,SpringLayout.SOUTH, scrollPaneNameSpace);
        layout.putConstraint(SpringLayout.SOUTH, scrollPaneLast,1000,SpringLayout.SOUTH, scrollPaneNameSpace);*/
        
        
		pack();
	}
	
	private void EnablePods() {
		panelCreating.removeAll();
		panelCenter.removeAll();
		panelLast.removeAll();
		String[] pods = {};
		try {
			pods = fetchPods();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int counter = 0; counter < pods.length; counter++) {
			if (pods[counter] != null) {
				panelCreating.add(createTextPane(pods[counter]));
				panelCreating.add(Box.createVerticalStrut(N));
			}
		}
		pack();
		repaint();
	
	}

	private void EnableCommands() {
		System.out.println(selectedPod);
		panelCenter.removeAll();
		Border blackline = BorderFactory.createTitledBorder(selectedPod);
		JButton paneDescribe = new JButton("Describe pod");
		panelCenter.add(paneDescribe);
		
		paneDescribe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// code you write in here will be executed if the button was pressed
				String cmd = event.getActionCommand();
				System.out.println(cmd);
				try {
					describePods();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(event);
			}
		});
		panelCenter.add(Box.createVerticalStrut(N));
		JButton paneLog = new JButton("Get Last 1000 logs");
		panelCenter.add(paneLog);
		paneLog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// code you write in here will be executed if the button was pressed
				String cmd = event.getActionCommand();
				System.out.println(cmd);
				try {
					getLast1000Logs();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(event);
			}
		});
		panelCenter.add(Box.createVerticalStrut(N));
		
		JButton paneCrashLog = new JButton("Get Last crashed logs");
		panelCenter.add(paneCrashLog);
		paneCrashLog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// code you write in here will be executed if the button was pressed
				String cmd = event.getActionCommand();
				System.out.println(cmd);
				try {
					getLastCrashLogs();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(event);
			}
		});
		panelCenter.add(Box.createVerticalStrut(N));
		panelCenter.setBorder(blackline);
		
		pack();
		repaint();
	}

	private JButton createTextPane(String text) {
		JButton pane = new JButton(text);

		pane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		pane.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// code you write in here will be executed if the button was pressed
				selectedPod = event.getActionCommand();
				System.out.println(selectedPod);
				EnableCommands();
				System.out.println(event);
			}
		});

		return pane;
	}

}
