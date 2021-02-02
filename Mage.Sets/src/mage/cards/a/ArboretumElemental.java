package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class ArboretumElemental extends CardImpl {

    public ArboretumElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{G}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
    }

    private ArboretumElemental(final ArboretumElemental card) {
        super(card);
    }

    @Override
    public ArboretumElemental copy() {
        return new ArboretumElemental(this);
    }
}
