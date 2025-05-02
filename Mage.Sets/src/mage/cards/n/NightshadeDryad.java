package mage.cards.n;

import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class NightshadeDryad extends CardImpl {

    public NightshadeDryad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.DRYAD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private NightshadeDryad(final NightshadeDryad card) {
        super(card);
    }

    @Override
    public NightshadeDryad copy() {
        return new NightshadeDryad(this);
    }
}
