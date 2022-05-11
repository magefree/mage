package mage.cards.i;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.BlackManaAbility;
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
public final class IndathaTriome extends CardImpl {

    public IndathaTriome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.PLAINS);
        this.subtype.add(SubType.SWAMP);
        this.subtype.add(SubType.FOREST);

        // ({T}: Add {W}, {B}, or {G}.)
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());

        // Indatha Triome enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Cycling {3}
        this.addAbility(new CyclingAbility(new GenericManaCost(3)));
    }

    private IndathaTriome(final IndathaTriome card) {
        super(card);
    }

    @Override
    public IndathaTriome copy() {
        return new IndathaTriome(this);
    }
}
