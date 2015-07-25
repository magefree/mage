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
package mage.sets.magic2015;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.AttackedByCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GetEmblemTargetPlayerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class GarrukApexPredator extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another target planeswalker");

    static {
        filter.add(new CardTypePredicate(CardType.PLANESWALKER));
        filter.add(new AnotherPredicate());
    }

    public GarrukApexPredator(UUID ownerId) {
        super(ownerId, 210, "Garruk, Apex Predator", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{5}{B}{G}");
        this.expansionSetCode = "M15";
        this.subtype.add("Garruk");

        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(5)), false));

        // +1: Destroy another target planeswalker.
        LoyaltyAbility ability = new LoyaltyAbility(new DestroyTargetEffect(), 1);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // +1: Put a 3/3 black Beast creature token with deathtouch onto the battlefield.
        ability = new LoyaltyAbility(new CreateTokenEffect(new GarrukApexPredatorBeastToken()), 1);
        this.addAbility(ability);

        // -3: Destroy target creature. You gain life equal to its toughness.
        ability = new LoyaltyAbility(new DestroyTargetEffect(), -3);
        ability.addEffect(new GarrukApexPredatorEffect3());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // -8: Target opponent gets an emblem with "Whenever a creature attacks you, it gets +5/+5 and gains trample until end of turn."
        Effect effect = new GetEmblemTargetPlayerEffect(new GarrukApexPredatorEmblem());
        effect.setText("Target opponent gets an emblem with \"Whenever a creature attacks you, it gets +5/+5 and gains trample until end of turn.\"");
        ability = new LoyaltyAbility(effect, -8);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public GarrukApexPredator(final GarrukApexPredator card) {
        super(card);
    }

    @Override
    public GarrukApexPredator copy() {
        return new GarrukApexPredator(this);
    }
}

class GarrukApexPredatorEffect3 extends OneShotEffect {

    public GarrukApexPredatorEffect3() {
        super(Outcome.GainLife);
        this.staticText = "You gain life equal to its toughness";
    }

    public GarrukApexPredatorEffect3(final GarrukApexPredatorEffect3 effect) {
        super(effect);
    }

    @Override
    public GarrukApexPredatorEffect3 copy() {
        return new GarrukApexPredatorEffect3(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent creature = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if (player != null && creature != null) {
            player.gainLife(creature.getToughness().getValue(), game);
            return true;
        }
        return false;
    }
}

class GarrukApexPredatorBeastToken extends Token {

    public GarrukApexPredatorBeastToken() {
        super("Beast", "3/3 black Beast creature token with deathtouch");
        setOriginalExpansionSetCode("M15");
        setTokenType(1);
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add("Beast");
        power = new MageInt(3);
        toughness = new MageInt(3);

        abilities.add(DeathtouchAbility.getInstance());

    }

}

/**
 * Emblem with "Whenever a creature attacks you, it gets +5/+5 and gains trample
 * until end of turn."
 */
class GarrukApexPredatorEmblem extends Emblem {

    public GarrukApexPredatorEmblem() {
        setName("EMBLEM: Garruk, Apex Predator");
        Effect effect = new BoostTargetEffect(5, 5, Duration.EndOfTurn);
        effect.setText("it gets +5/+5");
        Ability ability = new AttackedByCreatureTriggeredAbility(Zone.COMMAND, effect, false, SetTargetPointer.PERMANENT);
        effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn,
                "and gains trample until end of turn");
        ability.addEffect(effect);
        this.getAbilities().add(ability);
    }
}
