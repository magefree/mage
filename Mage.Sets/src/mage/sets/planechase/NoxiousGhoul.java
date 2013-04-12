/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.planechase;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.continious.BoostAllEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author jeffwadsworth
 */
public class NoxiousGhoul extends CardImpl<NoxiousGhoul> {

    final FilterPermanent filter = new FilterPermanent("Noxious Ghoul or another Zombie");
    final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("non-Zombie");

    public NoxiousGhoul(UUID ownerId) {
        super(ownerId, 35, "Noxious Ghoul", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.expansionSetCode = "HOP";
        this.subtype.add("Zombie");

        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        
        filter.add(Predicates.or(
                new CardIdPredicate(this.getId()),
                new SubtypePredicate("Zombie")));
        
        filter2.add(new CardTypePredicate(CardType.CREATURE));
        filter2.add(Predicates.not(
                new SubtypePredicate("Zombie")));
        
        final String rule = "Whenever Noxious Ghoul or another Zombie enters the battlefield, all non-Zombie creatures get -1/-1 until end of turn.";

        // Whenever Noxious Ghoul or another Zombie enters the battlefield, all non-Zombie creatures get -1/-1 until end of turn.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new BoostAllEffect(-1, -1, Duration.EndOfTurn, filter2, false), filter, false, rule));
    }

    public NoxiousGhoul(final NoxiousGhoul card) {
        super(card);
    }

    @Override
    public NoxiousGhoul copy() {
        return new NoxiousGhoul(this);
    }
}
