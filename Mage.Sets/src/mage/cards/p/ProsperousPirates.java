package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProsperousPirates extends CardImpl {

    public ProsperousPirates(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Prosperous Pirates enters the battlefield, create two colorless Treasure artifact tokens with "{T}, Sacrifice this artifact: Add one mana of any color."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new TreasureToken(), 2)));
    }

    private ProsperousPirates(final ProsperousPirates card) {
        super(card);
    }

    @Override
    public ProsperousPirates copy() {
        return new ProsperousPirates(this);
    }
}
