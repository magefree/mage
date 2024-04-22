package mage.cards.o;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;

public final class Overcome extends CardImpl {

    public Overcome(UUID ownerId, CardSetInfo cardSetInfo){
        super(ownerId, cardSetInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");

        // Creatures you control get +2/+2 and gain trample until end of turn.
        Effect effect = new BoostControlledEffect(2, 2, Duration.EndOfTurn);
        effect.setText("Creatures you control get +2/+2");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, new FilterCreaturePermanent());
        effect.setText("and gain trample until end of turn");
        this.getSpellAbility().addEffect(effect);
    }

    private Overcome(final Overcome overcome){
        super(overcome);
    }

    public Overcome copy(){
        return new Overcome(this);
    }
}
