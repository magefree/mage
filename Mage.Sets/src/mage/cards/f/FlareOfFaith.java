package mage.cards.f;

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
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlareOfFaith extends CardImpl {

    public FlareOfFaith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Target creature gets +2/+2 until end of turn. If it's a Human, it gets +3/+3 and gains indestructible until end of turn instead.
        this.getSpellAbility().addEffect(new FlareOfFaithEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FlareOfFaith(final FlareOfFaith card) {
        super(card);
    }

    @Override
    public FlareOfFaith copy() {
        return new FlareOfFaith(this);
    }
}

class FlareOfFaithEffect extends OneShotEffect {

    FlareOfFaithEffect() {
        super(Outcome.Benefit);
        staticText = "target creature gets +2/+2 until end of turn. If it's a Human, " +
                "instead it gets +3/+3 and gains indestructible until end of turn";
    }

    private FlareOfFaithEffect(final FlareOfFaithEffect effect) {
        super(effect);
    }

    @Override
    public FlareOfFaithEffect copy() {
        return new FlareOfFaithEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        if (permanent.hasSubtype(SubType.HUMAN, game)) {
            game.addEffect(new BoostTargetEffect(3, 3), source);
            game.addEffect(new GainAbilityTargetEffect(
                    IndestructibleAbility.getInstance(), Duration.EndOfTurn
            ), source);
        } else {
            game.addEffect(new BoostTargetEffect(2, 2), source);
        }
        return true;
    }
}
