package bash;

import java.awt.EventQueue;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Bash extends JFrame
{

	public Bash()
	{
		add(new Screen());
		setSize(1000, 600);
		setTitle("Bash");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				new Bash().setVisible(true);
			}
		});
	}
}
