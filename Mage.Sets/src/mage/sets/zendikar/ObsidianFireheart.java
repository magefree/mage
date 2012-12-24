/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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

package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LevelX2
 */
public class ObsidianFireheart extends CardImpl<ObsidianFireheart> {

    private static final String rule = "For as long as that land has a blaze counter on it, it has \"At the beginning of your upkeep, this land deals 1 damage to you.\" <i>(The land continues to burn after Obsidian Fireheart has left the battlefield.)</i>";
    private static final FilterLandPermanent filter = new FilterLandPermanent("land without a blaze counter on it");

    static {
        filter.add(Predicates.not(new CounterPredicate(CounterType.BLAZE)));
    }

    public ObsidianFireheart(UUID ownerId) {
        super(ownerId, 140, "Obsidian Fireheart", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{1}{R}{R}{R}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Elemental");
        this.color.setRed(true);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {1}{R}{R}: Put a blaze counter on target land without a blaze counter on it.
        // For as long as that land has a blaze counter on it, it has "At the beginning
        // of your upkeep, this land deals 1 damage to you." (The land continues to burn
        // after Obsidian Fireheart has left the battlefield.)
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.BLAZE.createInstance()),new ManaCostsImpl("{1}{R}{R}{R}"));
        ability.addTarget(new TargetLandPermanent(filter));
        Effect effect = new ObsidianFireheartGainAbilityEffect(
                new BeginningOfUpkeepTriggeredAbility(
                    new DamageControllerEffect(1),
                    TargetController.YOU,
                    false),
                Duration.Custom, rule);
        ability.addEffect(effect);
        this.addAbility(ability);

    }

    public ObsidianFireheart(final ObsidianFireheart card) {
        super(card);
    }

    @Override
    public ObsidianFireheart copy() {
        return new ObsidianFireheart(this);
    }
}


class ObsidianFireheartGainAbilityEffect extends GainAbilityTargetEffect {

    public ObsidianFireheartGainAbilityEffect(Ability ability, Duration duration, String rule) {
        super(ability, duration, rule);
    }

    public ObsidianFireheartGainAbilityEffect(final ObsidianFireheartGainAbilityEffect effect) {
        super(effect);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        Permanent land = game.getPermanent(this.targetPointer.getFirst(game, source));
        if (land != null && land.getCounters().getCount(CounterType.BLAZE) < 1) {
            return true;
        }
        return false;
    }

    @Override
    public ObsidianFireheartGainAbilityEffect copy() {
        return new ObsidianFireheartGainAbilityEffect(this);
    }
}
