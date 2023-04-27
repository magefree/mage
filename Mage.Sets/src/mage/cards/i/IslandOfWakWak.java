
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author anonymous
 */
public final class IslandOfWakWak extends CardImpl {

    private static final FilterCreaturePermanent filterWithFlying = new FilterCreaturePermanent("creature with flying");

    static {
        filterWithFlying.add(new AbilityPredicate(FlyingAbility.class));
    }

    public IslandOfWakWak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Target creature with flying has base power 0 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new IslandOfWakWakEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filterWithFlying));
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

    public IslandOfWakWakEffect() {
        super(Outcome.Detriment);
        staticText = "Target creature with flying has base power 0 until end of turn.";
    }

    public IslandOfWakWakEffect(final IslandOfWakWakEffect effect) {
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
    public Effect copy() {
        return new IslandOfWakWakEffect(this);
    }
}
