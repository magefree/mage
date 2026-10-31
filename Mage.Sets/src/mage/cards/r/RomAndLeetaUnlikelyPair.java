package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class RomAndLeetaUnlikelyPair extends CardImpl {

    public RomAndLeetaUnlikelyPair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FERENGI);
        this.subtype.add(SubType.BAJORAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Seek out -- Whenever you draw your second card each turn, create two tapped Treasure tokens.
        this.addAbility(new DrawNthCardTriggeredAbility(new CreateTokenEffect(new TreasureToken(), 2, true))
            .withFlavorWord("Seek out"));
    }

    private RomAndLeetaUnlikelyPair(final RomAndLeetaUnlikelyPair card) {
        super(card);
    }

    @Override
    public RomAndLeetaUnlikelyPair copy() {
        return new RomAndLeetaUnlikelyPair(this);
    }
}
