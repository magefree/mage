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
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EquippedMultipleSourceCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Saga
 */
public class BalanWanderingKnight extends CardImpl {
    
    private static final String rule = "{this} has double strike as long as two or more Equipment are attached to it.";

    public BalanWanderingKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Cat");
        this.subtype.add("Knight");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // First Strike
        this.addAbility(FirstStrikeAbility.getInstance());
        
        // Balan, Wandering Knight has double strike as long as two or more Equipment are attached to it.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance()), EquippedMultipleSourceCondition.instance, rule);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        
        // {1}{W}: Attach all Equipment you control to Balan.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BalanWanderingKnightEffect(), new ManaCostsImpl("{1}{W}")));
    }

    public BalanWanderingKnight(final BalanWanderingKnight card) {
        super(card);
    }

    @Override
    public BalanWanderingKnight copy() {
        return new BalanWanderingKnight(this);
    }

    static class BalanWanderingKnightEffect extends OneShotEffect {

        public BalanWanderingKnightEffect() {
            super(Outcome.Benefit);
            this.staticText = "Attach all Equipment you control to {this}.";
        }

        public BalanWanderingKnightEffect(final BalanWanderingKnightEffect effect) {
            super(effect);
        }

        @Override
        public BalanWanderingKnightEffect copy() {
            return new BalanWanderingKnightEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent balan = game.getPermanent(source.getSourceId());
            if (balan != null) {
                FilterPermanent filter = new FilterPermanent();
                filter.add(new SubtypePredicate(SubType.EQUIPMENT));
                for (Permanent equipment : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(),game)) {
                    if (equipment != null) {
                        //If an Equipment can’t equip, it isn’t attached, and it doesn’t become unattached (if it’s attached to a creature).
                        if (!balan.cantBeAttachedBy(equipment, game)) {
                            balan.addAttachment(equipment.getId(), game);
                        }
                    }
                }
                return true;
            }
            return false;
        }
    }
}