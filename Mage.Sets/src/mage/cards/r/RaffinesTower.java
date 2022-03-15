package mage.cards.r;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RaffinesTower extends CardImpl {

    public RaffinesTower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.PLAINS);
        this.subtype.add(SubType.ISLAND);
        this.subtype.add(SubType.SWAMP);

        // ({T}: Add {W}, {U}, or {B}.)
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());

        // Raffine's Tower enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Cycling {3}
        this.addAbility(new CyclingAbility(new GenericManaCost(3)));
    }

    private RaffinesTower(final RaffinesTower card) {
        super(card);
    }

    @Override
    public RaffinesTower copy() {
        return new RaffinesTower(this);
    }
}
