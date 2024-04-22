package mage.cards.c;

import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.counter.AddPoisonCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author North
 */
public final class CaressOfPhyrexia extends CardImpl {

    public CaressOfPhyrexia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        this.getSpellAbility().addEffect(new DrawCardTargetEffect(3));
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(3).setText(", loses 3 life"));
        this.getSpellAbility().addEffect(new AddPoisonCounterTargetEffect(3).setText(", and gets three poison counters"));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private CaressOfPhyrexia(final CaressOfPhyrexia card) {
        super(card);
    }

    @Override
    public CaressOfPhyrexia copy() {
        return new CaressOfPhyrexia(this);
    }
}
