package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ChromaCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.ManaType;
import mage.constants.SubType;
import mage.game.permanent.token.GoatToken;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class SpringjackShepherd extends CardImpl {

    public SpringjackShepherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Chroma - When Springjack Shepherd enters the battlefield, create a 0/1 white Goat creature token for each white mana symbol in the mana costs of permanents you control.
        DynamicValue xValue = new ChromaCount(ManaType.WHITE);
        Effect effect = new CreateTokenEffect(new GoatToken(), xValue);
        effect.setText("create a 0/1 white Goat creature token for each white mana symbol in the mana costs of permanents you control.");
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                effect, false)
                .setAbilityWord(AbilityWord.CHROMA)
                .addHint(new ValueHint("White mana symbols in your permanents", xValue))
        );
    }

    private SpringjackShepherd(final SpringjackShepherd card) {
        super(card);
    }

    @Override
    public SpringjackShepherd copy() {
        return new SpringjackShepherd(this);
    }
}