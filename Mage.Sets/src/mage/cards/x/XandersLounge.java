package mage.cards.x;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class XandersLounge extends CardImpl {

    public XandersLounge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.ISLAND);
        this.subtype.add(SubType.SWAMP);
        this.subtype.add(SubType.MOUNTAIN);

        // ({T}: Add {U}, {B}, or {R}.)
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());

        // Xander's Lounge enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Cycling {3}
        this.addAbility(new CyclingAbility(new GenericManaCost(3)));
    }

    private XandersLounge(final XandersLounge card) {
        super(card);
    }

    @Override
    public XandersLounge copy() {
        return new XandersLounge(this);
    }
}
