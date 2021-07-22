
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetSpell;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class PatronWizard extends CardImpl {

    static final private FilterControlledPermanent filter = new FilterControlledPermanent("untapped Wizard you control");

    static {
        filter.add(SubType.WIZARD.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public PatronWizard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Tap an untapped Wizard you control: Counter target spell unless its controller pays {1}.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterUnlessPaysEffect(new GenericManaCost(1)), new TapTargetCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
    }

    private PatronWizard(final PatronWizard card) {
        super(card);
    }

    @Override
    public PatronWizard copy() {
        return new PatronWizard(this);
    }
}
