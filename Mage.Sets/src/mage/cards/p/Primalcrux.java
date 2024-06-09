package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ChromaCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Primalcrux extends CardImpl {

    public Primalcrux(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}{G}{G}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Chroma - Primalcrux's power and toughness are each equal to the number of green mana symbols in the mana costs of permanents you control.
        DynamicValue xValue = new ChromaCount(ManaType.GREEN);
        Effect effect = new SetBasePowerToughnessSourceEffect(xValue);
        effect.setText("<i>Chroma</i> &mdash; Primalcrux's power and toughness are each equal to the number of green mana symbols in the mana costs of permanents you control.");
        this.addAbility(new SimpleStaticAbility(Zone.ALL, effect)
                .addHint(new ValueHint("Green mana symbols in your permanents", xValue))
        );
    }

    private Primalcrux(final Primalcrux card) {
        super(card);
    }

    @Override
    public Primalcrux copy() {
        return new Primalcrux(this);
    }
}
