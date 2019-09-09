package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.MouseToken;

/**
 *
 * @author TheElk801
 */
public final class EnchantedCarriage extends CardImpl {

    public EnchantedCarriage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");
        
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Enchanted Carriage enters the battlefield, create two 1/1 white Mouse creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MouseToken(),2)));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private EnchantedCarriage(final EnchantedCarriage card) {
        super(card);
    }

    @Override
    public EnchantedCarriage copy() {
        return new EnchantedCarriage(this);
    }
}
