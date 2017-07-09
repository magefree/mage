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

import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class FilterLandPermanent extends FilterPermanent {

    public FilterLandPermanent() {
        this("land");
    }

    public FilterLandPermanent(String name) {
        super(name);
        this.add(new CardTypePredicate(CardType.LAND));
    }

    public FilterLandPermanent(SubType subtype, String name) {
        super(name);
        this.add(new CardTypePredicate(CardType.LAND));
        this.add(new SubtypePredicate(subtype));
    }

    public static FilterLandPermanent nonbasicLand() {
        FilterLandPermanent filter = new FilterLandPermanent("nonbasic land");
        filter.add(Predicates.not(new SupertypePredicate(SuperType.BASIC)));
        return filter;
    }

    public static FilterLandPermanent nonbasicLands() {
        FilterLandPermanent filter = new FilterLandPermanent("nonbasic lands");
        filter.add(Predicates.not(new SupertypePredicate(SuperType.BASIC)));
        return filter;
    }

    public FilterLandPermanent(final FilterLandPermanent filter) {
        super(filter);
    }

    @Override
    public FilterLandPermanent copy() {
        return new FilterLandPermanent(this);
    }
}
