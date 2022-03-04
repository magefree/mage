package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SparasHeadquarters extends CardImpl {

    public SparasHeadquarters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.FOREST);
        this.subtype.add(SubType.PLAINS);
        this.subtype.add(SubType.ISLAND);

        // ({T}: Add {G}, {W}, or {U}.)
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());

        // Spara's Headquarters enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Cycling {3}
        this.addAbility(new CyclingAbility(new GenericManaCost(3)));
    }

    private SparasHeadquarters(final SparasHeadquarters card) {
        super(card);
    }

    @Override
    public SparasHeadquarters copy() {
        return new SparasHeadquarters(this);
    }
}
