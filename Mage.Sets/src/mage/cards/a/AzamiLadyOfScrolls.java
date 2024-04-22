

package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 * @author Loki
 */
public final class AzamiLadyOfScrolls extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped Wizard you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.WIZARD.getPredicate());
    }

    public AzamiLadyOfScrolls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(0);
        this.toughness = new MageInt(2);
        
        // Tap an untapped Wizard you control: Draw a card.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new TapTargetCost(new TargetControlledPermanent(1, 1, filter, false))));
    }

    private AzamiLadyOfScrolls(final AzamiLadyOfScrolls card) {
        super(card);
    }

    @Override
    public AzamiLadyOfScrolls copy() {
        return new AzamiLadyOfScrolls(this);
    }

}
