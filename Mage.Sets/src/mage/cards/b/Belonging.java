package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.EncoreAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ShapeshifterColorlessToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Belonging extends CardImpl {

    public Belonging(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.INCARNATION);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When this creature enters, create three 1/1 colorless Shapeshifter creature tokens with changeling.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ShapeshifterColorlessToken(), 3)));

        // Encore {6}{W}{W}
        this.addAbility(new EncoreAbility(new ManaCostsImpl<>("{6}{W}{W}")));
    }

    private Belonging(final Belonging card) {
        super(card);
    }

    @Override
    public Belonging copy() {
        return new Belonging(this);
    }
}
