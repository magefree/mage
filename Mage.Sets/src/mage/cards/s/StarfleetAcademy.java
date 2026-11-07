package mage.cards.s;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.FederationCount;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author muz
 */
public final class StarfleetAcademy extends CardImpl {

    public StarfleetAcademy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // Federation -- {5}, {T}: Choose a color. Add an amount of mana of that color equal to the number of creature types among non-Borg creatures you control.
        Ability ability = new DynamicManaAbility(
            Mana.AnyMana(1),
            FederationCount.instance,
            new GenericManaCost(5),
            "Choose a color. Add an amount of mana of that color equal to the number of creature types among non-Borg creatures you control",
            true
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability.setAbilityWord(AbilityWord.FEDERATION).addHint(FederationCount.instance.getHint()));
    }

    private StarfleetAcademy(final StarfleetAcademy card) {
        super(card);
    }

    @Override
    public StarfleetAcademy copy() {
        return new StarfleetAcademy(this);
    }
}
