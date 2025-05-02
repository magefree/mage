package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.AttacksWhileSaddledTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.SaddleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GildedGhoda extends CardImpl {

    public GildedGhoda(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HORSE);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever this creature attacks while saddled, create a Treasure token.
        this.addAbility(new AttacksWhileSaddledTriggeredAbility(new CreateTokenEffect(new TreasureToken())));

        // Saddle 1
        this.addAbility(new SaddleAbility(1));
    }

    private GildedGhoda(final GildedGhoda card) {
        super(card);
    }

    @Override
    public GildedGhoda copy() {
        return new GildedGhoda(this);
    }
}
