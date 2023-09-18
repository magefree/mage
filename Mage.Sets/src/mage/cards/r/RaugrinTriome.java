package mage.cards.r;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.BlueManaAbility;
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
public final class RaugrinTriome extends CardImpl {

    public RaugrinTriome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.ISLAND);
        this.subtype.add(SubType.MOUNTAIN);
        this.subtype.add(SubType.PLAINS);

        // ({T}: Add {U}, {R}, or {W}.)
        this.addAbility(new BlueManaAbility());
        this.addAbility(new RedManaAbility());
        this.addAbility(new WhiteManaAbility());

        // Raugrin Triome enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Cycling {3}
        this.addAbility(new CyclingAbility(new GenericManaCost(3)));
    }

    private RaugrinTriome(final RaugrinTriome card) {
        super(card);
    }

    @Override
    public RaugrinTriome copy() {
        return new RaugrinTriome(this);
    }
}
