package mexica.multiplayer.client;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Gui extends JPanel implements ListSelectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4411411810259770448L;
	private JList<String> list;
	private DefaultListModel<String> listModel;

	private static final String joinString = "Join";
	private static final String refreshString = "Refresh";
	private JButton refreshButton;
	private JTextField gameLobbyName;

	public Gui() {
		super(new BorderLayout());

		listModel = new DefaultListModel<String>();
		listModel.addElement("Jane Doe");
		listModel.addElement("John Smith");
		listModel.addElement("Kathy Green");

		// Create the list and put it in a scroll pane.
		list = new JList<String>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.addListSelectionListener(this);
		list.setVisibleRowCount(10);
		JScrollPane listScrollPane = new JScrollPane(list);

		JButton joinButton = new JButton(joinString);
		JoinListener joinListener = new JoinListener(joinButton);
		joinButton.setActionCommand(joinString);
		joinButton.addActionListener(joinListener);
		joinButton.setEnabled(false);

		refreshButton = new JButton(refreshString);
		refreshButton.setActionCommand(refreshString);
		refreshButton.addActionListener(new RefreshListener());

		gameLobbyName = new JTextField(10);
		gameLobbyName.addActionListener(joinListener);
		gameLobbyName.getDocument().addDocumentListener(joinListener);
		String name = listModel.getElementAt(list.getSelectedIndex())
				.toString();

		// Create a panel that uses BoxLayout.
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.add(refreshButton);
		buttonPane.add(Box.createHorizontalStrut(5));
		buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
		buttonPane.add(Box.createHorizontalStrut(5));
		buttonPane.add(gameLobbyName);
		buttonPane.add(joinButton);
		buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		add(listScrollPane, BorderLayout.CENTER);
		add(buttonPane, BorderLayout.PAGE_END);
	}

	class RefreshListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// This method can be called only if
			// there's a valid selection
			// so go ahead and remove whatever's selected.
			int index = list.getSelectedIndex();
			listModel.remove(index);

			int size = listModel.getSize();

			if (size == 0) { // Nobody's left, disable firing.
				refreshButton.setEnabled(false);

			} else { // Select an index.
				if (index == listModel.getSize()) {
					// removed item in last position
					index--;
				}

				list.setSelectedIndex(index);
				list.ensureIndexIsVisible(index);
			}
		}
	}

	// This listener is shared by the text field and the hire button.
	class JoinListener implements ActionListener, DocumentListener {
		private boolean alreadyEnabled = false;
		private JButton button;

		public JoinListener(JButton button) {
			this.button = button;
		}

		// Required by ActionListener.
		public void actionPerformed(ActionEvent e) {
			String name = gameLobbyName.getText();

			// User didn't type in a unique name...
			if (name.equals("") || alreadyInList(name)) {
				Toolkit.getDefaultToolkit().beep();
				gameLobbyName.requestFocusInWindow();
				gameLobbyName.selectAll();
				return;
			}

			int index = list.getSelectedIndex(); // get selected index
			if (index == -1) { // no selection, so insert at beginning
				index = 0;
			} else { // add after the selected item
				index++;
			}

			listModel.insertElementAt(gameLobbyName.getText(), index);
			// If we just wanted to add to the end, we'd do this:
			// listModel.addElement(employeeName.getText());

			// Reset the text field.
			gameLobbyName.requestFocusInWindow();
			gameLobbyName.setText("");

			// Select the new item and make it visible.
			list.setSelectedIndex(index);
			list.ensureIndexIsVisible(index);
		}

		// This method tests for string equality. You could certainly
		// get more sophisticated about the algorithm. For example,
		// you might want to ignore white space and capitalization.
		protected boolean alreadyInList(String name) {
			return listModel.contains(name);
		}

		// Required by DocumentListener.
		public void insertUpdate(DocumentEvent e) {
			enableButton();
		}

		// Required by DocumentListener.
		public void removeUpdate(DocumentEvent e) {
			handleEmptyTextField(e);
		}

		// Required by DocumentListener.
		public void changedUpdate(DocumentEvent e) {
			if (!handleEmptyTextField(e)) {
				enableButton();
			}
		}

		private void enableButton() {
			if (!alreadyEnabled) {
				button.setEnabled(true);
			}
		}

		private boolean handleEmptyTextField(DocumentEvent e) {
			if (e.getDocument().getLength() <= 0) {
				button.setEnabled(false);
				alreadyEnabled = false;
				return true;
			}
			return false;
		}
	}

	// This method is required by ListSelectionListener.
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {

			if (list.getSelectedIndex() == -1) {
				// No selection, disable fire button.
				refreshButton.setEnabled(false);

			} else {
				// Selection, enable the fire button.
				refreshButton.setEnabled(true);
			}
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("ListDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		JComponent newContentPane = new Gui();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
