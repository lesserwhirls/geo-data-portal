package gov.usgs.cida.gdp.wps.algorithm.heuristic;

import org.junit.BeforeClass;
import org.junit.Test;
import ucar.nc2.Dimension;
import ucar.nc2.dt.GridDatatype;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author jiwalker
 */
public class TimingHeuristicGridCellVisitorTest {
	
	private static GridDatatype datatype;
	
	@BeforeClass
	public static void setupClass() {
		datatype = mock(GridDatatype.class);
		Dimension timeDim = mock(Dimension.class);
		when(timeDim.getLength()).thenReturn(5);
		when(datatype.getTimeDimension()).thenReturn(timeDim);
	}
	
	@Test
	public void testTEnd() {
		TotalTimeAlgorithmHeuristic instance = new TotalTimeAlgorithmHeuristic(1);
		instance.tStart(1);
		instance.tEnd(1);
		assertThat(instance.traverseContinue(), is(true));
	}

	@Test
	public void testTStart() {
		TotalTimeAlgorithmHeuristic instance = new TotalTimeAlgorithmHeuristic(1);
		boolean result = instance.tStart(1);
		assertThat(result, is(true));
	}

	@Test
	public void testEstimateTotalTime() throws InterruptedException {
		TotalTimeAlgorithmHeuristic instance = new TotalTimeAlgorithmHeuristic(2, 1, Long.MAX_VALUE);
		instance.traverseStart(datatype);
		instance.tStart(1);
		Thread.sleep(500);
		instance.tEnd(1);
		long result = instance.estimateTotalTime();
		assertThat(result, is(equalTo(5000l)));
	}
	
	@Test(expected = RuntimeException.class)
	public void testExceedsTotalTime() throws InterruptedException {
		TotalTimeAlgorithmHeuristic instance = new TotalTimeAlgorithmHeuristic(2, 1, 4999);
		instance.tStart(1);
		Thread.sleep(500);
		instance.tEnd(1);
	}
}
