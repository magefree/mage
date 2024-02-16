package mage.cards.c;

import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

public final class Charge extends CardImpl {

    public Charge(UUID ownerId, CardSetInfo cardSetInfo){
        super(ownerId, cardSetInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Creatures you control get +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 1, Duration.EndOfTurn));

    }


    private Charge(final Charge charge){
        super(charge);
    }

    @Override
    public Charge copy(){
        return new Charge(this);
    }
}
