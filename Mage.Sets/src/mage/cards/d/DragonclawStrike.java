package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DragonclawStrike extends CardImpl {

    public DragonclawStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2/G}{2/U}{2/R}");

        // Double the power and toughness of target creature you control until end of turn. Then it fights up to one target creature an opponent controls.
        this.getSpellAbility().addEffect(new DragonclawStrikeEffect());
        this.getSpellAbility().addEffect(new FightTargetsEffect()
                .setText("Then it fights up to one target creature an opponent controls"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent(0, 1));
    }

    private DragonclawStrike(final DragonclawStrike card) {
        super(card);
    }

    @Override
    public DragonclawStrike copy() {
        return new DragonclawStrike(this);
    }
}

class DragonclawStrikeEffect extends OneShotEffect {

    DragonclawStrikeEffect() {
        super(Outcome.Benefit);
        staticText = "double the power and toughness of target creature you control until end of turn";
    }

    private DragonclawStrikeEffect(final DragonclawStrikeEffect effect) {
        super(effect);
    }

    @Override
    public DragonclawStrikeEffect copy() {
        return new DragonclawStrikeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        game.addEffect(new BoostTargetEffect(
                permanent.getPower().getValue(),
                permanent.getToughness().getValue()
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}
