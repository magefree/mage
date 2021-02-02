
package mage.cards.j;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author noxx
 */
public final class JointAssault extends CardImpl {

    public JointAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Target creature gets +2/+2 until end of turn. If it's paired with a creature, that creature also gets +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new JointAssaultBoostTargetEffect(2, 2, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private JointAssault(final JointAssault card) {
        super(card);
    }

    @Override
    public JointAssault copy() {
        return new JointAssault(this);
    }
}

class JointAssaultBoostTargetEffect extends ContinuousEffectImpl {

    private final int power;
    private final int toughness;
    private MageObjectReference paired;

    public JointAssaultBoostTargetEffect(int power, int toughness, Duration duration) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
        staticText = "Target creature gets +2/+2 until end of turn. If it's paired with a creature, that creature also gets +2/+2 until end of turn";
    }

    public JointAssaultBoostTargetEffect(final JointAssaultBoostTargetEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
    }

    @Override
    public JointAssaultBoostTargetEffect copy() {
        return new JointAssaultBoostTargetEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        UUID permanentId = targetPointer.getFirst(game, source);
        Permanent target = game.getPermanent(permanentId);
        if (target != null) {
            if (target.getPairedCard() != null) {
                this.paired = target.getPairedCard();
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        UUID permanentId = targetPointer.getFirst(game, source);

        Permanent target = game.getPermanent(permanentId);
        if (target != null) {
            target.addPower(power);
            target.addToughness(toughness);
            affectedTargets++;
        }

        if (this.paired != null) {
            Permanent pairedPermanent = this.paired.getPermanent(game);
            if (pairedPermanent != null) {
                pairedPermanent.addPower(power);
                pairedPermanent.addToughness(toughness);
                affectedTargets++;
            }
        }

        return affectedTargets > 0;
    }
}
