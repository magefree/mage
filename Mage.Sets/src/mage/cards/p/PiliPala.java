
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.common.UntapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class PiliPala extends CardImpl {

    public PiliPala(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.SCARECROW);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {2}, {untap}: Add one mana of any color.
        Ability ability = new AnyColorManaAbility(new ManaCostsImpl<>("{2}"));
        ability.addCost(new UntapSourceCost());
        this.addAbility(ability);
    }

    private PiliPala(final PiliPala card) {
        super(card);
    }

    @Override
    public PiliPala copy() {
        return new PiliPala(this);
    }
}
