
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class Resuscitate extends CardImpl {

    public Resuscitate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Until end of turn, creatures you control gain "{1}: Regenerate this creature."
        Ability ability = new SimpleActivatedAbility(new RegenerateSourceEffect().setText("Regenerate this creature"), new GenericManaCost(1));
        this.getSpellAbility().addEffect(
                new GainAbilityAllEffect(ability, Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES,
                        "Until end of turn, creatures you control gain \"{1}: Regenerate this creature.\""
                )
        );
    }

    private Resuscitate(final Resuscitate card) {
        super(card);
    }

    @Override
    public Resuscitate copy() {
        return new Resuscitate(this);
    }
}
