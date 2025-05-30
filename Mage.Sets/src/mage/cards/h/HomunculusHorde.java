package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HomunculusHorde extends CardImpl {

    public HomunculusHorde(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HOMUNCULUS);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you draw your second card each turn, create a token that's a copy of this creature.
        this.addAbility(new DrawNthCardTriggeredAbility(new CreateTokenCopySourceEffect(), false, 2));
    }

    private HomunculusHorde(final HomunculusHorde card) {
        super(card);
    }

    @Override
    public HomunculusHorde copy() {
        return new HomunculusHorde(this);
    }
}
