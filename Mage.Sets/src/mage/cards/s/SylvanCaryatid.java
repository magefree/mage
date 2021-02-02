
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class SylvanCaryatid extends CardImpl {

    public SylvanCaryatid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.PLANT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

    }

    private SylvanCaryatid(final SylvanCaryatid card) {
        super(card);
    }

    @Override
    public SylvanCaryatid copy() {
        return new SylvanCaryatid(this);
    }
}
