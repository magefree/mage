package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RapaciousDragon extends CardImpl {

    public RapaciousDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Rapacious Dragon enters the battlefield, create two Treasure tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new TreasureToken(), 2)
                        .setText("create two Treasure tokens. <i>(They’re artifacts with " +
                                "\"{T}, Sacrifice this artifact: Add one mana of any color.\")</i>")
        ));
    }

    private RapaciousDragon(final RapaciousDragon card) {
        super(card);
    }

    @Override
    public RapaciousDragon copy() {
        return new RapaciousDragon(this);
    }
}
