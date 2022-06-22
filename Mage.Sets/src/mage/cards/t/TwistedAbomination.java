
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.SwampcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class TwistedAbomination extends CardImpl {

    public TwistedAbomination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MUTANT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // {B}: Regenerate Twisted Abomination.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{B}")));
        // Swampcycling {2}
        this.addAbility(new SwampcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private TwistedAbomination(final TwistedAbomination card) {
        super(card);
    }

    @Override
    public TwistedAbomination copy() {
        return new TwistedAbomination(this);
    }
}
