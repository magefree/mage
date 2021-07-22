
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class OnduWarCleric extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an untapped Ally you control");

    static {
        filter.add(SubType.ALLY.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public OnduWarCleric(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Cohort</i> &mdash; {T}, Tap an untapped Ally you control: You gain 2 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(2), new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(filter)));
        ability.setAbilityWord(AbilityWord.COHORT);
        this.addAbility(ability);
    }

    private OnduWarCleric(final OnduWarCleric card) {
        super(card);
    }

    @Override
    public OnduWarCleric copy() {
        return new OnduWarCleric(this);
    }
}
