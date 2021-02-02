
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class ArgivianBlacksmith extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("artifact creature");
    
    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(CardType.CREATURE.getPredicate());
    }

    public ArgivianBlacksmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Prevent the next 2 damage that would be dealt to target artifact creature this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new PreventDamageToTargetEffect(Duration.EndOfTurn, 2), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ArgivianBlacksmith(final ArgivianBlacksmith card) {
        super(card);
    }

    @Override
    public ArgivianBlacksmith copy() {
        return new ArgivianBlacksmith(this);
    }
}
