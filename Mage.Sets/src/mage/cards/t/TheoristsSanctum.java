package mage.cards.t;

import java.util.UUID;
import mage.constants.SubType;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.abilities.costs.common.BeholdCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TheoristsSanctum extends CardImpl {

    public TheoristsSanctum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.ISLAND);

        // ({T}: Add {U}.)
        this.addAbility(new BlueManaAbility());

        // As this land enters, you may behold a Jace. If you don't, this land enters tapped.
        this.addAbility(new AsEntersBattlefieldAbility(new TapSourceUnlessPaysEffect(new BeholdCost(SubType.JACE)), "you may behold a Jace. If you don't, this land enters tapped"));

        // {2}{U}, {T}: Empower Jace 2.
        Ability ability = new SimpleActivatedAbility(new EmpowerJaceEffect(2), new ManaCostsImpl<>("{2}{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TheoristsSanctum(final TheoristsSanctum card) {
        super(card);
    }

    @Override
    public TheoristsSanctum copy() {
        return new TheoristsSanctum(this);
    }
}
