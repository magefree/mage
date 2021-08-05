package mage.cards.s;

import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StepThrough extends CardImpl {

    private static final FilterCard filter = new FilterCard("Wizard");

    static {
        filter.add(SubType.WIZARD.getPredicate());
    }

    public StepThrough(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        // Return two target creatures to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2));

        // Wizardcycling {2}
        this.addAbility(new CyclingAbility(new GenericManaCost(2), filter, "Wizardcycling"));
    }

    private StepThrough(final StepThrough card) {
        super(card);
    }

    @Override
    public StepThrough copy() {
        return new StepThrough(this);
    }
}
