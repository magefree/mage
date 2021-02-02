
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class RubblebackRhino extends CardImpl {

    public RubblebackRhino(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.subtype.add(SubType.RHINO);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Hexproof (This creature can't be the target of spells or abilities your opponents control.)
        this.addAbility(HexproofAbility.getInstance());
    }

    private RubblebackRhino(final RubblebackRhino card) {
        super(card);
    }

    @Override
    public RubblebackRhino copy() {
        return new RubblebackRhino(this);
    }
}
