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
package mage.sets.commander2014;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class TeferiTemporalArchmage extends CardImpl {

    public TeferiTemporalArchmage(UUID ownerId) {
        super(ownerId, 19, "Teferi, Temporal Archmage", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{4}{U}{U}");
        this.expansionSetCode = "C14";
        this.subtype.add("Teferi");

        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(5)), false));

        // +1: Look at the top two cards of your library. Put one of them into your hand and the other on the bottom of your library.
        this.addAbility(new LoyaltyAbility(new LookLibraryAndPickControllerEffect(
                new StaticValue(2), false, new StaticValue(1), new FilterCard(), Zone.LIBRARY, false, false), 1));

        // -1: Untap up to four target permanents.
        LoyaltyAbility loyaltyAbility = new LoyaltyAbility(new UntapTargetEffect(), -1);
        loyaltyAbility.addTarget(new TargetPermanent(0, 4, new FilterPermanent(), false));
        this.addAbility(loyaltyAbility);

        // -10: You get an emblem with "You may activate loyalty abilities of planeswalkers you control on any player's turn any time you could cast an instant."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new TeferiTemporalArchmageEmblem()), -10));

        // Teferi, Temporal Archmage can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());

    }

    public TeferiTemporalArchmage(final TeferiTemporalArchmage card) {
        super(card);
    }

    @Override
    public TeferiTemporalArchmage copy() {
        return new TeferiTemporalArchmage(this);
    }
}

class TeferiTemporalArchmageEmblem extends Emblem {

    // "You may activate loyalty abilities of planeswalkers you control on any player's turn any time you could cast an instant."

    public TeferiTemporalArchmageEmblem() {
        this.setName("EMBLEM: Teferi, Temporal Archmage");
        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, new TeferiTemporalArchmageAsThoughEffect()));
    }
}

class TeferiTemporalArchmageAsThoughEffect extends AsThoughEffectImpl {

    public TeferiTemporalArchmageAsThoughEffect() {
        super(AsThoughEffectType.ACTIVATE_AS_INSTANT, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may activate loyalty abilities of planeswalkers you control on any player's turn any time you could cast an instant";
    }

    public TeferiTemporalArchmageAsThoughEffect(final TeferiTemporalArchmageAsThoughEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TeferiTemporalArchmageAsThoughEffect copy() {
        return new TeferiTemporalArchmageAsThoughEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game) {
        if (affectedAbility.getControllerId().equals(source.getControllerId()) && affectedAbility instanceof LoyaltyAbility) {
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return false; // Not used
    }

}
