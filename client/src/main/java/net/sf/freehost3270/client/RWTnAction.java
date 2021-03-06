/*
 * FreeHost3270 a suite of terminal 3270 access utilities.
 * Copyright (C) 1998, 2001  Art Gillespie
 * Copyright (2) 2005 the http://FreeHost3270.Sourceforge.net
 *                        Project Contributors.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */


package net.sf.freehost3270.client;

/**
 * Provides a callback to consumers from the RWTelnet object. When developing
 * a 3270 client using the RightHost APIs, you must implement this interface
 * and pass it to the RW3270 Object when it's instantiated.  It provides
 * several important methods that the 3270 engine calls to inform the
 * implementation of 3270 events.
 *
 * @see net.sf.freehost3270.client.RW3270
 */
public interface RWTnAction {
    /**
     * Status constant passed to <code>status()</code> indicating that the
     * client's connection to the host has been closed.
     */
    public static final int DISCONNECTED_BY_REMOTE_HOST = 0;

    /**
     * Status constant passed to <code>status()</code> indicating that there
     * was a problem connecting to the 3270 host or SessionServer
     */
    public static final int CONNECTION_ERROR = 1;

    /**
     * Status constant passed to <code>status()</code> indicating that the
     * 3270 engine is waiting for a response from the 3270 host.
     */
    public static final int X_WAIT = 2;

    /**
     * Status constant passed to <code>status()</code> indicating that the
     * 3270 engine is ready for user input
     */
    public static final int READY = 3;

    /**
     * This method is called when the host application sends the beep command.
     * It should fire some sort of audible alarm on the client's system.
     * For example:
     * <pre>
     * public void beep() {
     *    Toolkit.getDefaultToolkit().beep();
     * }
     * </pre>
     */
    public void beep();

    /**
     * This method is called when the SessionServer admin sends a broadcast
     * message to all currently connected clients.
     * 
     * <p>
     * If you're implementing Broadcast Messaging in your client, this method
     * should paint <code>String msg</code> to the client's screen.
     * </p>
     *
     * @param msg The broadcast message from the system administrator.
     */
    public void broadcastMessage(String msg);

    /**
     * This method is called when the cursor position (current buffer
     * position) of the 3270 engine is changed by host commands or by user
     * input.  When drawing the cursor to the client's display, use this
     * method to fire cursor draw logic.
     * 
     * <p>
     * This is useful for 'undrawing' the cursor before drawing it in its new
     * position.  The position is returned as an integer value between 0
     * (first character position) and <code>len(dataBuffer) - 1</code> (last
     * character position).  For example using 3270 Model 2.  The value would
     * be between 0 and 1919.
     * </p>
     *
     * @param oldPos The previous cursor position.
     * @param newPos The new (current) cursor position.
     */
    public void cursorMove(int oldPos, int newPos);

    /**
     * Is called by the 3270 telnet engine when it has received new data.
     * 
     * <p>
     * This method would usually call a paint() method or other display
     * update.  Note: this method is useful when the client implementation is
     * stateful.  If the 3270 engine does not have direct access to the
     * user's display, the RW3270.waitForNewData() method will suspend the
     * calling thread until new data arrives.
     * </p>
     */
    public void incomingData();

    /**
     * This method is called by the 3270 engine to communicate status changes
     * to the client.  The possible values for int are outlined in the
     * constants for this field.
     * 
     * <ul>
     * <li>
     * RWtnAction.DISCONNECTED_BY_REMOTE_HOST: This status message indicates
     * that the connection to the host was lost.
     * </li>
     * <li>
     * RWTnAction.CONNECTION_ERROR: This status message that there was a
     * problem connecting to the SessionServer or that the SessionServer had
     * trouble connecting to the 3270 host.
     * </li>
     * <li>
     * RWTnAction.X_WAIT: This status message means the 3270 engine is waiting
     * for a response from the 3270 host.
     * </li>
     * <li>
     * RWTnAction.READY: This status message means the 3270 engine is ready
     * for input from the client implementation.
     * </li>
     * </ul>
     * 
     *
     * @param msg The status message
     */
    public void status(int msg);
}
