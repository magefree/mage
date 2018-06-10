

package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
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
public final class ObsidianFireheart extends CardImpl {

    private static final String rule = "For as long as that land has a blaze counter on it, it has \"At the beginning of your upkeep, this land deals 1 damage to you.\" <i>(The land continues to burn after Obsidian Fireheart has left the battlefield.)</i>";
    private static final FilterLandPermanent filter = new FilterLandPermanent("land without a blaze counter on it");

    static {
        filter.add(Predicates.not(new CounterPredicate(CounterType.BLAZE)));
    }

    public ObsidianFireheart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);


        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {1}{R}{R}: Put a blaze counter on target land without a blaze counter on it.
        // For as long as that land has a blaze counter on it, it has "At the beginning
        // of your upkeep, this land deals 1 damage to you." (The land continues to burn
        // after Obsidian Fireheart has left the battlefield.)
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.BLAZE.createInstance()),new ManaCostsImpl("{1}{R}{R}"));
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
        if (land != null && land.getCounters(game).getCount(CounterType.BLAZE) < 1) {
            return true;
        }
        return false;
    }

    @Override
    public ObsidianFireheartGainAbilityEffect copy() {
        return new ObsidianFireheartGainAbilityEffect(this);
    }
}
