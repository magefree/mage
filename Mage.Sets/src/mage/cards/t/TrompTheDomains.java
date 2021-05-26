
package mage.cards.t;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.common.DomainHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class TrompTheDomains extends CardImpl {

    public TrompTheDomains(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{G}");

        // Domain - Until end of turn, creatures you control gain trample and get +1/+1 for each basic land type among lands you control.
        Effect effect = new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("<i>Domain</i> &mdash; Until end of turn, creatures you control gain trample");
        this.getSpellAbility().addEffect(effect);
        DynamicValue domain = new DomainValue();
        effect = new BoostControlledEffect(domain, domain, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE, false);
        effect.setText("and get +1/+1 for each basic land type among lands you control");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addHint(DomainHint.instance);

    }

    private TrompTheDomains(final TrompTheDomains card) {
        super(card);
    }

    @Override
    public TrompTheDomains copy() {
        return new TrompTheDomains(this);
    }
}
