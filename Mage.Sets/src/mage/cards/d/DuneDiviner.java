
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author ciaccona007
 */
public final class DuneDiviner extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an untapped Desert you control");

    static {
        filter.add(SubType.DESERT.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }
    
    public DuneDiviner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        
        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {1}, Tap an untapped Desert you control: You gain 1 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(1), new GenericManaCost(1));
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    private DuneDiviner(final DuneDiviner card) {
        super(card);
    }

    @Override
    public DuneDiviner copy() {
        return new DuneDiviner(this);
    }
}
