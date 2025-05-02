package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.EldraziSpawnToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class EmrakulsMessenger extends CardImpl {

    public EmrakulsMessenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you draw your second card each turn, create a 0/1 colorless Eldrazi Spawn creature token with "Sacrifice this creature: Add {C}."
        this.addAbility(new DrawNthCardTriggeredAbility(
                new CreateTokenEffect(new EldraziSpawnToken()), false, 2
        ));
    }

    private EmrakulsMessenger(final EmrakulsMessenger card) {
        super(card);
    }

    @Override
    public EmrakulsMessenger copy() {
        return new EmrakulsMessenger(this);
    }
}
