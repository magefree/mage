
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.InsectToken;

/**
 *
 * @author Temba21
 */
public final class SymbioticBeast extends CardImpl {

    public SymbioticBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
      
        // When Symbiotic Beast dies, create four 1/1 green Insect creature tokens.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new InsectToken(), 4)));
    }

    private SymbioticBeast(final SymbioticBeast card) {
        super(card);
    }

    @Override
    public SymbioticBeast copy() {
        return new SymbioticBeast(this);
    }
}
