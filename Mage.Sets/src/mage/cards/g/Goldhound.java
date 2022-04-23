
package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author Hiddevb
 */
public final class Goldhound extends CardImpl {

    public Goldhound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.TREASURE);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // First Strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility(true));

        // {T}, Sacrifice Goldhound: Add one mana of any color.
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private Goldhound(final Goldhound card) {
        super(card);
    }

    @Override
    public Goldhound copy() {
        return new Goldhound(this);
    }
}
