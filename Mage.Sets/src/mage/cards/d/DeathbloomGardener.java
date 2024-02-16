package mage.cards.d;

import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeathbloomGardener extends CardImpl {

    public DeathbloomGardener(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private DeathbloomGardener(final DeathbloomGardener card) {
        super(card);
    }

    @Override
    public DeathbloomGardener copy() {
        return new DeathbloomGardener(this);
    }
}
