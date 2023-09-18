package mage.cards.z;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZiatorasProvingGround extends CardImpl {

    public ZiatorasProvingGround(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.SWAMP);
        this.subtype.add(SubType.MOUNTAIN);
        this.subtype.add(SubType.FOREST);

        // ({T}: Add {B}, {R}, or {G}.)
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());

        // Ziatora's Proving Ground enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Cycling {3}
        this.addAbility(new CyclingAbility(new GenericManaCost(3)));
    }

    private ZiatorasProvingGround(final ZiatorasProvingGround card) {
        super(card);
    }

    @Override
    public ZiatorasProvingGround copy() {
        return new ZiatorasProvingGround(this);
    }
}
