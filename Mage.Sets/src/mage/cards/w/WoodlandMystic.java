package mage.cards.w;

import mage.MageInt;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WoodlandMystic extends CardImpl {

    public WoodlandMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private WoodlandMystic(final WoodlandMystic card) {
        super(card);
    }

    @Override
    public WoodlandMystic copy() {
        return new WoodlandMystic(this);
    }
}
