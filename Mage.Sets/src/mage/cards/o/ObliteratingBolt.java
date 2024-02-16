package mage.cards.o;

import java.util.UUID;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author weirddan455
 */
public final class ObliteratingBolt extends CardImpl {

    public ObliteratingBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Obliterating Bolt deals 4 damage to target creature or planeswalker. If that creature or planeswalker would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addEffect(new ExileTargetIfDiesEffect("creature or planeswalker"));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private ObliteratingBolt(final ObliteratingBolt card) {
        super(card);
    }

    @Override
    public ObliteratingBolt copy() {
        return new ObliteratingBolt(this);
    }
}
