package mage.cards.z;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZagothTriome extends CardImpl {

    public ZagothTriome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.SWAMP);
        this.subtype.add(SubType.FOREST);
        this.subtype.add(SubType.ISLAND);

        // ({T}: Add {B}, {G}, or {U}.)
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());

        // Zagoth Triome enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Cycling {3}
        this.addAbility(new CyclingAbility(new GenericManaCost(3)));
    }

    private ZagothTriome(final ZagothTriome card) {
        super(card);
    }

    @Override
    public ZagothTriome copy() {
        return new ZagothTriome(this);
    }
}
