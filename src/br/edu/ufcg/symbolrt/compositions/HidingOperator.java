/*
 * SYmbolic Model-Based test case generation toOL for Real-Time systems (SYMBOLRT)
 * (C) Copyright 2010-2012 Federal University of Campina Grande (UFCG)
 * 
 * This file is part of SYMBOLRT.
 *
 * SYMBOLRT is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SYMBOLRT is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SYMBOLRT.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * REVISION HISTORY:
 * Author                           Date           Brief Description
 * -------------------------------- -------------- ------------------------------
 * Wilkerson de Lucena Andrade      16/06/2011     An old class named TIOSTSPreprocessing (Version 1.2) was divided into two classes. This class only contains the synchronous algorithm algorithm (Version 1.0).
 * 
 */

package br.edu.ufcg.symbolrt.compositions;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import br.edu.ufcg.symbolrt.base.Action;
import br.edu.ufcg.symbolrt.base.ClockGuard;
import br.edu.ufcg.symbolrt.base.Location;
import br.edu.ufcg.symbolrt.base.SimpleClockGuard;
import br.edu.ufcg.symbolrt.base.TIOSTS;
import br.edu.ufcg.symbolrt.base.Transition;
import br.edu.ufcg.symbolrt.base.TypedData;
import br.edu.ufcg.symbolrt.compositions.exceptions.IncompatibleCompositionalOperationException;
import br.edu.ufcg.symbolrt.util.Constants;


/**
 * <code>SynchronousProduct</code> Class. <br>
 * This class contains an algorithm needed to perform the sequential composition between two TIOSTSs.
 * 
 * @author Adriana Carla Damasceno  ( <a href="mailto:adriana@copin.ufcg.edu.br">adriana@copin.ufcg.edu.br</a> )
 * 
 * @version 1.0
 * <br>
 * SYmbolic Model-Based test case generation toOL for Real-Time systems (SYMBOLRT)
 * <br>
 * (C) Copyright 2010-2012 Federal University of Campina Grande (UFCG)
 * <br>
 * <a href="https://sites.google.com/a/computacao.ufcg.edu.br/symbolrt">https://sites.google.com/a/computacao.ufcg.edu.br/symbolrt</a>
 */
public class HidingOperator {

	private static HidingOperator instance = null;

	private HidingOperator() {
	}

	/**
	 * Method used to obtain an instance of this class.
	 * @return An instance of {@link HidingOperator}.
	 */
	public static HidingOperator getInstance() {
		if (instance == null) {
			instance = new HidingOperator();
		}
		return instance;
	}

	/**
	 * Returns the sequential composition between two TIOSTSs.
	 * @param t1 The first TIOSTS.
	 * @param t1 The second TIOSTS.
	 * @return The sequential composition between t1 and t2.
	 * @throws IncompatibleSequentialComposition If t1 do not fit conditions demanded to it.  
	 */
	public TIOSTS hidingOperator(TIOSTS model1, String aName) throws IncompatibleCompositionalOperationException {

		TIOSTS tiostsRen = null;
		
		RenamingOperator renOperator = RenamingOperator.getInstance();
		try {
			tiostsRen = renOperator.renamingOperator(model1, aName, "Tal");
		} catch (IncompatibleCompositionalOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tiostsRen;
		
	}

}