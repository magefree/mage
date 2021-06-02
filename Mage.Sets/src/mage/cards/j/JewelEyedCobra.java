package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JewelEyedCobra extends CardImpl {

    public JewelEyedCobra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Jewel-Eyed Cobra dies, create a Treasure token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new TreasureToken())));
    }

    private JewelEyedCobra(final JewelEyedCobra card) {
        super(card);
    }

    @Override
    public JewelEyedCobra copy() {
        return new JewelEyedCobra(this);
    }
}
