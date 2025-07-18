package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class IslandOfWakWak extends CardImpl {

    public IslandOfWakWak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Target creature with flying has base power 0 until end of turn.
        Ability ability = new SimpleActivatedAbility(new IslandOfWakWakEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
        this.addAbility(ability);
    }

    private IslandOfWakWak(final IslandOfWakWak card) {
        super(card);
    }

    @Override
    public IslandOfWakWak copy() {
        return new IslandOfWakWak(this);
    }
}

class IslandOfWakWakEffect extends OneShotEffect {

    IslandOfWakWakEffect() {
        super(Outcome.Detriment);
        staticText = "Target creature with flying has base power 0 until end of turn.";
    }

    private IslandOfWakWakEffect(final IslandOfWakWakEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (targetCreature != null) {
            int toughness = targetCreature.getToughness().getModifiedBaseValue();
            game.addEffect(new SetBasePowerToughnessTargetEffect(null, StaticValue.get(toughness), Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }

    @Override
    public IslandOfWakWakEffect copy() {
        return new IslandOfWakWakEffect(this);
    }
}
