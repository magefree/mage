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

package mage.target.common;

import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetCreaturePermanent<T extends TargetCreaturePermanent<T>> extends TargetPermanent<TargetCreaturePermanent<T>> {

	public TargetCreaturePermanent() {
		this(1, 1, FilterCreaturePermanent.getDefault(), false);
	}

	public TargetCreaturePermanent(FilterCreaturePermanent filter) {
		this(1, 1, filter, false);
	}

	public TargetCreaturePermanent(int numTargets) {
		this(numTargets, numTargets, FilterCreaturePermanent.getDefault(), false);
	}

	public TargetCreaturePermanent(int minNumTargets, int maxNumTargets, FilterCreaturePermanent filter, boolean notTarget) {
		super(1, 1, filter, notTarget);
		this.targetName = filter.getMessage();
	}

	public TargetCreaturePermanent(final TargetCreaturePermanent target) {
		super(target);
	}

	@Override
	public TargetCreaturePermanent copy() {
		return new TargetCreaturePermanent(this);
	}

}
