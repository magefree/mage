package mage.cards.s;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class SorinsThirst extends CardImpl {

    public SorinsThirst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{B}");

        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addEffect(new GainLifeEffect(2).concatBy("and"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public SorinsThirst(final SorinsThirst card) {
        super(card);
    }

    @Override
    public SorinsThirst copy() {
        return new SorinsThirst(this);
    }

}
