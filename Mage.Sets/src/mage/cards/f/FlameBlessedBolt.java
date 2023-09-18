package mage.cards.f;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlameBlessedBolt extends CardImpl {

    public FlameBlessedBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Flame-Blessed Bolt deals 2 damage to target creature or planeswalker. If that creature or planeswalker would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addEffect(new ExileTargetIfDiesEffect()
                .setText("If that creature or planeswalker would die this turn, exile it instead."));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private FlameBlessedBolt(final FlameBlessedBolt card) {
        super(card);
    }

    @Override
    public FlameBlessedBolt copy() {
        return new FlameBlessedBolt(this);
    }
}
