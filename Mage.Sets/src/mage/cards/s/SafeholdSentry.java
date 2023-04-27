
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.UntapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author jeffwadsworth

 */
public final class SafeholdSentry extends CardImpl {

    public SafeholdSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}{W}, {untap}: Safehold Sentry gets +0/+2 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(0, 2, Duration.EndOfTurn), new ManaCostsImpl<>("{2}{W}"));
        ability.addCost(new UntapSourceCost());
        this.addAbility(ability);
        
    }

    private SafeholdSentry(final SafeholdSentry card) {
        super(card);
    }

    @Override
    public SafeholdSentry copy() {
        return new SafeholdSentry(this);
    }
}
