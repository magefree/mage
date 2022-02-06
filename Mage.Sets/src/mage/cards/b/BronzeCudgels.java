package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.AbilityResolutionCountHint;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.AbilityResolvedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BronzeCudgels extends CardImpl {

    public BronzeCudgels(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // {2}: Equipped creature gets +X/+0 until end of turn, where X is the number of times this ability has resolved this turn.
        this.addAbility(new SimpleActivatedAbility(
                new BronzeCudgelsEffect(), new GenericManaCost(2)
        ).addHint(AbilityResolutionCountHint.instance), new AbilityResolvedWatcher());

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private BronzeCudgels(final BronzeCudgels card) {
        super(card);
    }

    @Override
    public BronzeCudgels copy() {
        return new BronzeCudgels(this);
    }
}

class BronzeCudgelsEffect extends OneShotEffect {

    BronzeCudgelsEffect() {
        super(Outcome.Benefit);
        staticText = "until end of turn, equipped creature gets +X/+0, " +
                "where X is the number of times this ability has resolved this turn.";
    }

    private BronzeCudgelsEffect(final BronzeCudgelsEffect effect) {
        super(effect);
    }

    @Override
    public BronzeCudgelsEffect copy() {
        return new BronzeCudgelsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent == null || game.getPermanent(permanent.getAttachedTo()) == null) {
            return false;
        }
        int resolvedCount = AbilityResolvedWatcher.getResolutionCount(game, source);
        if (resolvedCount < 1) {
            return false;
        }
        game.addEffect(new BoostTargetEffect(resolvedCount, 0)
                .setTargetPointer(new FixedTarget(permanent.getOwnerId(), game)), source);
        return true;
    }
}
