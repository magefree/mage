package mage.cards.t;

import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.common.DomainHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TrompTheDomains extends CardImpl {

    public TrompTheDomains(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{G}");

        // Domain - Until end of turn, creatures you control gain trample and get +1/+1 for each basic land type among lands you control.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("until end of turn, creatures you control gain trample"));
        this.getSpellAbility().addEffect(new BoostControlledEffect(
                DomainValue.REGULAR, DomainValue.REGULAR, Duration.EndOfTurn
        ).setText("and get +1/+1 for each basic land type among lands you control"));
        this.getSpellAbility().addHint(DomainHint.instance);
        this.getSpellAbility().setAbilityWord(AbilityWord.DOMAIN);
    }

    private TrompTheDomains(final TrompTheDomains card) {
        super(card);
    }

    @Override
    public TrompTheDomains copy() {
        return new TrompTheDomains(this);
    }
}
