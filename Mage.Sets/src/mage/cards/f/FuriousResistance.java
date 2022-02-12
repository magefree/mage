package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterBlockingCreature;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class FuriousResistance extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterBlockingCreature("blocking creature");

    public FuriousResistance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Target blocking creature gets +3/+0 and gains first strike until end of turn.
        this.getSpellAbility().addEffect(new FuriousResistanceEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private FuriousResistance(final FuriousResistance card) {
        super(card);
    }

    @Override
    public FuriousResistance copy() {
        return new FuriousResistance(this);
    }
}

class FuriousResistanceEffect extends OneShotEffect {

    public FuriousResistanceEffect() {
        super(Outcome.BoostCreature);
        staticText = "Target blocking creature gets +3/+0 and gains first strike until end of turn";
    }

    public FuriousResistanceEffect(final FuriousResistanceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target == null) {
            return false;
        }

        ContinuousEffect effect = new BoostTargetEffect(3, 0, Duration.EndOfTurn);
        ContinuousEffect effect2 = new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
        effect.setTargetPointer(new FixedTarget(target.getId(), game));
        effect2.setTargetPointer(new FixedTarget(target.getId(), game));
        game.addEffect(effect, source);
        game.addEffect(effect2, source);
        return true;
    }

    @Override
    public FuriousResistanceEffect copy() {
        return new FuriousResistanceEffect(this);
    }
}
