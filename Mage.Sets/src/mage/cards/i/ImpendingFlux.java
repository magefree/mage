package mage.cards.i;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.SpellsCastNotFromHandValue;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.watchers.common.SpellsCastNotFromHandWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ImpendingFlux extends CardImpl {

    private static final DynamicValue xValue = new IntPlusDynamicValue(1, SpellsCastNotFromHandValue.instance);

    public ImpendingFlux(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Paradox -- Impending Flux deals X damage to each opponent and each creature they control, where X is 1 plus the number of spells you've cast from anywhere other than your hand this turn.
        this.getSpellAbility().addEffect(new DamagePlayersEffect(
                xValue, TargetController.OPPONENT
        ).setText("{this} deals X damage to each opponent"));
        this.getSpellAbility().addEffect(new DamageAllEffect(
                xValue, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE
        ).setText("and each creature they control, where X is 1 plus the number " +
                "of spells you've cast from anywhere other than your hand this turn"));
        this.getSpellAbility().setAbilityWord(AbilityWord.PARADOX);
        this.getSpellAbility().addWatcher(new SpellsCastNotFromHandWatcher());
        this.getSpellAbility().addHint(SpellsCastNotFromHandValue.getHint());

        // Foretell {1}{R}{R}
        this.addAbility(new ForetellAbility(this, "{1}{R}{R}"));
    }

    private ImpendingFlux(final ImpendingFlux card) {
        super(card);
    }

    @Override
    public ImpendingFlux copy() {
        return new ImpendingFlux(this);
    }
}
