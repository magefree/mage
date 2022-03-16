package mage.cards.k;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.BlueManaAbility;
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
public final class KetriaTriome extends CardImpl {

    public KetriaTriome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.FOREST);
        this.subtype.add(SubType.ISLAND);
        this.subtype.add(SubType.MOUNTAIN);

        // ({T}: Add {G}, {U}, or {R}.)
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());
        this.addAbility(new RedManaAbility());

        // Ketria Triome enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Cycling {3}
        this.addAbility(new CyclingAbility(new GenericManaCost(3)));
    }

    private KetriaTriome(final KetriaTriome card) {
        super(card);
    }

    @Override
    public KetriaTriome copy() {
        return new KetriaTriome(this);
    }
}
