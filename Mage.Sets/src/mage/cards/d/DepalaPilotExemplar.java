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
package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public class DepalaPilotExemplar extends CardImpl {

    public DepalaPilotExemplar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Dwarf");
        this.subtype.add("Pilot");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Other Dwarves you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, new FilterCreaturePermanent(SubType.DWARF, "Dwarves"), true)));

        // Each Vehicle you control gets +1/+1 as long as it's a creature.
        Effect effect = new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, new FilterCreaturePermanent(SubType.VEHICLE, "Vehicle"));
        effect.setText("Each Vehicle you control gets +1/+1 as long as it's a creature");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // Whenever Depala, Pilot Exemplar becomes tapped, you may pay {X}. If you do, reveal the top X cards of your library, put all Dwarf and Vehicle cards from among them into your hand, then put the rest on the bottom of your library in a random order.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new DepalaPilotExemplarEffect(), true));
    }

    public DepalaPilotExemplar(final DepalaPilotExemplar card) {
        super(card);
    }

    @Override
    public DepalaPilotExemplar copy() {
        return new DepalaPilotExemplar(this);
    }
}

class DepalaPilotExemplarEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("Dwarf and Vehicle cards");

    static {
        filter.add(Predicates.or(new SubtypePredicate(SubType.DWARF), new SubtypePredicate(SubType.VEHICLE)));
    }

    DepalaPilotExemplarEffect() {
        super(Outcome.DrawCard);
        this.staticText = "pay {X}. If you do, reveal the top X cards of your library, put all Dwarf and Vehicle cards from among them into your hand, then put the rest on the bottom of your library in a random order";
    }

    DepalaPilotExemplarEffect(final DepalaPilotExemplarEffect effect) {
        super(effect);
    }

    @Override
    public DepalaPilotExemplarEffect copy() {
        return new DepalaPilotExemplarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ManaCosts<ManaCost> cost = new ManaCostsImpl<>("{X}");
            int xValue = controller.announceXMana(0, Integer.MAX_VALUE, "Choose the amount of mana to pay", game, source);
            cost.add(new GenericManaCost(xValue));
            if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false) && xValue > 0) {
                new RevealLibraryPutIntoHandEffect(xValue, filter, Zone.LIBRARY, false).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
