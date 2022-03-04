package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SavaiTriome extends CardImpl {

    public SavaiTriome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.MOUNTAIN);
        this.subtype.add(SubType.PLAINS);
        this.subtype.add(SubType.SWAMP);

        // ({T}: Add {R}, {W}, or {B}.)
        this.addAbility(new RedManaAbility());
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlackManaAbility());

        // Savai Triome enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Cycling {3}
        this.addAbility(new CyclingAbility(new GenericManaCost(3)));
    }

    private SavaiTriome(final SavaiTriome card) {
        super(card);
    }

    @Override
    public SavaiTriome copy() {
        return new SavaiTriome(this);
    }
}
