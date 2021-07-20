package mage.cards.m;

import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.MetalcraftHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class MirranMettle extends CardImpl {

    private static final String effectText = "<br><i>Metalcraft</i> &mdash; That creature gets +4/+4 until end of turn instead if you control three or more artifacts.";

    public MirranMettle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Target creature gets +2/+2 until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));

        // Metalcraft — That creature gets +4/+4 until end of turn instead if you control three or more artifacts.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn),
                new LockedInCondition(MetalcraftCondition.instance), effectText));
        this.getSpellAbility().addHint(MetalcraftHint.instance);
    }

    private MirranMettle(final MirranMettle card) {
        super(card);
    }

    @Override
    public MirranMettle copy() {
        return new MirranMettle(this);
    }
}
