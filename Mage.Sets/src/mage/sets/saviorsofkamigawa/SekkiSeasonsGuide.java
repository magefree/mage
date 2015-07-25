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
package mage.sets.saviorsofkamigawa;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiritToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public class SekkiSeasonsGuide extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("eight Spirits");

    static {
        filter.add(new SubtypePredicate("Spirit"));
    }

    public SekkiSeasonsGuide(UUID ownerId) {
        super(ownerId, 148, "Sekki, Seasons' Guide", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{G}{G}{G}");
        this.expansionSetCode = "SOK";
        this.supertype.add("Legendary");
        this.subtype.add("Spirit");

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Sekki, Seasons' Guide enters the battlefield with eight +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(8)), "with eight +1/+1 counters on it"));

        // If damage would be dealt to Sekki, prevent that damage, remove that many +1/+1 counters from Sekki, and put that many 1/1 colorless Spirit creature tokens onto the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SekkiSeasonsGuideEffect()));

        // Sacrifice eight Spirits: Return Sekki from your graveyard to the battlefield.
        this.addAbility(new SimpleActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToHandEffect(),
                new SacrificeTargetCost(new TargetControlledPermanent(8, 8, filter, true))));
    }

    public SekkiSeasonsGuide(final SekkiSeasonsGuide card) {
        super(card);
    }

    @Override
    public SekkiSeasonsGuide copy() {
        return new SekkiSeasonsGuide(this);
    }
}

class SekkiSeasonsGuideEffect extends PreventionEffectImpl {

    public SekkiSeasonsGuideEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false, false);
        staticText = "If damage would be dealt to {this}, prevent that damage, remove that many +1/+1 counters from {this}, and put that many 1/1 colorless Spirit creature tokens onto the battlefield";
    }

    public SekkiSeasonsGuideEffect(final SekkiSeasonsGuideEffect effect) {
        super(effect);
    }

    @Override
    public SekkiSeasonsGuideEffect copy() {
        return new SekkiSeasonsGuideEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int damage = event.getAmount();
        preventDamageAction(event, source, game);
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.removeCounters(CounterType.P1P1.createInstance(damage), game);
        }
        new CreateTokenEffect(new SpiritToken(), damage).apply(game, source);
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getSourceId())) {
                return true;
            }
        }
        return false;
    }

}
