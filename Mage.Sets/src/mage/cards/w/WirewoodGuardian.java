
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.ForestcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class WirewoodGuardian extends CardImpl {

    public WirewoodGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.MUTANT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Forestcycling {2}
        this.addAbility(new ForestcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private WirewoodGuardian(final WirewoodGuardian card) {
        super(card);
    }

    @Override
    public WirewoodGuardian copy() {
        return new WirewoodGuardian(this);
    }
}
