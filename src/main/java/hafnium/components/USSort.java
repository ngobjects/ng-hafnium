package hafnium.components;

import java.util.List;

import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SortOrder;

import ng.appserver.NGActionResults;
import ng.appserver.NGContext;
import ng.appserver.templating.NGComponent;

public class USSort extends NGComponent {

	public USSort( NGContext context ) {
		super( context );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	public boolean disabled() {
		final Boolean value = (Boolean)valueForBinding( "disabled" );
		return value != null && value;
	}

	public NGActionResults sort() {
		List<Ordering> orderings = orderings();
		final String keyPath = keyPath();

		Ordering newOrdering = new Ordering( keyPath, SortOrder.ASCENDING );

		if( orderings != null && !orderings.isEmpty() ) {
			final Ordering firstOrdering = orderings.get( 0 );

			if( firstOrdering.getSortSpecString().equals( keyPath ) ) {
				if( firstOrdering.isAscending() ) {
					newOrdering = new Ordering( keyPath, SortOrder.DESCENDING );
				}

				orderings.remove( 0 );
			}
		}

		orderings.add( 0, newOrdering );

		// Keep max 3 orderings
		while( orderings.size() > 3 ) {
			orderings.remove( orderings.size() - 1 );
		}

		setValueForBinding( orderings, "orderings" );

		return null;
	}

	private List<Ordering> orderings() {
		return (List<Ordering>)valueForBinding( "orderings" );
	}

	private String keyPath() {
		return (String)valueForBinding( "keyPath" );
	}

	/**
	 * @return The 1-based index of the given keyPath in the ordering list, or null if not present.
	 */
	public Integer indexOfKeyPath() {
		for( final Ordering ordering : orderings() ) {
			if( ordering.getSortSpecString().equals( keyPath() ) ) {
				return orderings().indexOf( ordering ) + 1;
			}
		}

		return null;
	}

	public boolean isUsedForOrdering() {
		for( final Ordering ordering : orderings() ) {
			if( ordering.getSortSpecString().equals( keyPath() ) ) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @return A unicode arrow indicating the current sort direction for this keyPath.
	 */
	public String currentSortIcon() {
		for( final Ordering ordering : orderings() ) {
			if( ordering.getSortSpecString().equals( keyPath() ) ) {
				return ordering.isAscending() ? "\u2191" : "\u2193";
			}
		}

		return null;
	}
}
