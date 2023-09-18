package mage.cards.w;

import mage.MageInt;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WeaverOfBlossoms extends CardImpl {

    public WeaverOfBlossoms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.b.BlossomCladWerewolf.class;

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // Daybound
        this.addAbility(new DayboundAbility());
    }

    private WeaverOfBlossoms(final WeaverOfBlossoms card) {
        super(card);
    }

    @Override
    public WeaverOfBlossoms copy() {
        return new WeaverOfBlossoms(this);
    }
}
