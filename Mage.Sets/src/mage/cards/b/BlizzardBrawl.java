package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlizzardBrawl extends CardImpl {

    public BlizzardBrawl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        this.addSuperType(SuperType.SNOW);

        // Choose target creature you control and target creature you don't control. If you control three or more snow permanents, the creature you control gets +1/+0 and gains indestructible until end of turn. Then those creatures fight each other.
        this.getSpellAbility().addEffect(new BlizzardBrawlEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private BlizzardBrawl(final BlizzardBrawl card) {
        super(card);
    }

    @Override
    public BlizzardBrawl copy() {
        return new BlizzardBrawl(this);
    }
}

class BlizzardBrawlEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    BlizzardBrawlEffect() {
        super(Outcome.Benefit);
        staticText = "Choose target creature you control and target creature you don't control. " +
                "If you control three or more snow permanents, the creature you control gets +1/+0 " +
                "and gains indestructible until end of turn. " +
                "Then those creatures fight each other." +
                "<i>(Each deals damage equal to its power to the other.)</i>";
    }

    private BlizzardBrawlEffect(final BlizzardBrawlEffect effect) {
        super(effect);
    }

    @Override
    public BlizzardBrawlEffect copy() {
        return new BlizzardBrawlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature1 = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        Permanent creature2 = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (creature1 == null) {
            return false;
        }
        if (game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game) >= 3) {
            game.addEffect(new BoostTargetEffect(
                    1, 0, Duration.EndOfTurn
            ).setTargetPointer(new FixedTarget(creature1.getId(), game)), source);
            game.addEffect(new GainAbilityTargetEffect(
                    IndestructibleAbility.getInstance(), Duration.EndOfTurn
            ).setTargetPointer(new FixedTarget(creature1.getId(), game)), source);
        }
        if (creature2 == null) {
            return true;
        }
        game.getState().processAction(game);
        return creature1.fight(creature2, source, game);
    }
}
