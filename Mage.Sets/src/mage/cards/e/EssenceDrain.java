package mage.cards.e;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Loki
 */
public final class EssenceDrain extends CardImpl {

    public EssenceDrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new GainLifeEffect(3).concatBy("and"));
    }

    public EssenceDrain(final EssenceDrain card) {
        super(card);
    }

    @Override
    public EssenceDrain copy() {
        return new EssenceDrain(this);
    }

}
