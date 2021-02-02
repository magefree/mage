
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class ShovingMatch extends CardImpl {

    public ShovingMatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Until end of turn, all creatures gain "{tap}: Tap target creature."
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(
                new GainAbilityAllEffect(ability, Duration.EndOfTurn, new FilterCreaturePermanent())
                        .setText("Until end of turn, all creatures gain \"{T}: Tap target creature.\"")
        );
    }

    private ShovingMatch(final ShovingMatch card) {
        super(card);
    }

    @Override
    public ShovingMatch copy() {
        return new ShovingMatch(this);
    }
}
