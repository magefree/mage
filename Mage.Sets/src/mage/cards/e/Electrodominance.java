package mage.cards.e;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.cost.CastWithoutPayingManaCostEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Electrodominance extends CardImpl {

    public Electrodominance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}{R}");

        // Electrodominance deals X damage to any target. You may cast a card with converted mana cost X or less from your hand without paying its mana cost.
        this.getSpellAbility().addEffect(new DamageTargetEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new CastWithoutPayingManaCostEffect(ManacostVariableValue.REGULAR));
    }

    private Electrodominance(final Electrodominance card) {
        super(card);
    }

    @Override
    public Electrodominance copy() {
        return new Electrodominance(this);
    }
}
