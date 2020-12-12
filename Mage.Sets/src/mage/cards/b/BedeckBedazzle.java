package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.common.TargetOpponentOrPlaneswalker;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BedeckBedazzle extends SplitCard {

    public BedeckBedazzle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B/R}{B/R}", "{4}{B}{R}", SpellAbilityType.SPLIT);

        // Bedeck
        // Target creature gets +3/-3 until end of turn.
        this.getLeftHalfCard().getSpellAbility().addEffect(
                new BoostTargetEffect(3, -3, Duration.EndOfTurn)
        );
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Bedazzle
        // Destroy target nonbasic land. Bedazzle deals 2 damage to target opponent or planeswalker.
        this.getRightHalfCard().getSpellAbility().addEffect(new BedazzleEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetLandPermanent());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetOpponentOrPlaneswalker());
    }

    private BedeckBedazzle(final BedeckBedazzle card) {
        super(card);
    }

    @Override
    public BedeckBedazzle copy() {
        return new BedeckBedazzle(this);
    }
}

class BedazzleEffect extends OneShotEffect {

    BedazzleEffect() {
        super(Outcome.Benefit);
        staticText = "Destroy target nonbasic land. {this} deals 2 damage to target opponent or planeswalker.";
    }

    private BedazzleEffect(final BedazzleEffect effect) {
        super(effect);
    }

    @Override
    public BedazzleEffect copy() {
        return new BedazzleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.destroy(source, game, false);
        }
        Effect effect = new DamageTargetEffect(StaticValue.get(2), true, "", true);
        effect.setTargetPointer(new FixedTarget(source.getTargets().get(1).getFirstTarget(), game));
        effect.apply(game, source);
        return true;
    }
}