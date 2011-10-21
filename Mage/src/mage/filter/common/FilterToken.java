/*
* Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
import mage.game.permanent.PermanentToken;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FilterToken<T extends FilterToken<T>> extends FilterCreaturePermanent<FilterToken<T>> {

	public FilterToken() {
		this("creature token");
	}

	public FilterToken(String name) {
		super(name);
	}

	public FilterToken(final FilterToken<T> filter) {
		super(filter);
	}

	/**
	 * There are a lot of usages of this method, we should rip them out as we see
	 * them and replace them with <code>new FilterToken()</code>.  This
	 * use to return a static instance of this object which is bad as its completely
	 * mutable and leads to EXTREMELY hard to track down issues!
	 */
	@Deprecated
	public static FilterToken getDefault() {
		return new FilterToken();
	}

	@Override
	public boolean match(Permanent permanent) {
        if (!(permanent instanceof PermanentToken))
            return notFilter;
		if (!super.match(permanent))
			return notFilter;

		return !notFilter;
	}

	@Override
	public FilterToken<T> copy() {
		return new FilterToken<T>(this);
	}
}
