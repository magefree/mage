/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.filter.common;

import mage.Constants.CardType;
import mage.filter.FilterPermanent;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FilterCreaturePermanent<T extends FilterCreaturePermanent<T>> extends FilterPermanent<FilterCreaturePermanent<T>> {

	protected static FilterCreaturePermanent defaultFilter = new FilterCreaturePermanent();

	protected boolean useAttacking;
	protected boolean attacking;
	protected boolean useBlocking;
	protected boolean blocking;
	protected boolean useTapped;
	protected boolean tapped;

	public FilterCreaturePermanent() {
		this("creature");
	}

	public FilterCreaturePermanent(String name) {
		super(name);
		cardType.add(CardType.CREATURE);
	}

	public FilterCreaturePermanent(final FilterCreaturePermanent<T> filter) {
		super(filter);
		this.useAttacking = filter.useAttacking;
		this.attacking = filter.attacking;
		this.useBlocking = filter.useBlocking;
		this.blocking = filter.blocking;
		this.useTapped = filter.useTapped;
		this.tapped = filter.tapped;
	}

	public static FilterCreaturePermanent getDefault() {
		return defaultFilter;
	}

	@Override
	public boolean match(Permanent permanent) {
		if (!super.match(permanent))
			return notFilter;

		if (useAttacking && permanent.isAttacking() != attacking)
			return notFilter;

		if (useBlocking && (permanent.getBlocking() > 0) != blocking)
			return notFilter;

		if (useTapped && permanent.isTapped() != tapped)
			return notFilter;

		return !notFilter;
	}

	public void setUseAttacking ( boolean useAttacking ) {
		this.useAttacking = useAttacking;
	}

	public void setAttacking ( boolean attacking ) {
		this.attacking = attacking;
	}

	public void setUseBlocking ( boolean useBlocking ) {
		this.useBlocking = useBlocking;
	}

	public void setBlocking ( boolean blocking ) {
		this.blocking = blocking;
	}

	public void setUseTapped ( boolean useTapped ) {
		this.useTapped = useTapped;
	}

	public void setTapped ( boolean tapped ) {
		this.tapped = tapped;
	}

	@Override
	public FilterCreaturePermanent<T> copy() {
		if (this == defaultFilter)
			return this;
		return new FilterCreaturePermanent<T>(this);
	}
}
