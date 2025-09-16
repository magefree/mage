package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class BlasphemousEdict extends CardImpl {

    public BlasphemousEdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // You may pay {B} rather than pay this spell's mana cost if there are thirteen or more creatures on the battlefield.
        Ability ability = new AlternativeCostSourceAbility(new ManaCostsImpl<>("{B}"), new PermanentsOnTheBattlefieldCondition(
                new FilterCreaturePermanent("there are thirteen or more creatures on the battlefield"),
                ComparisonType.OR_GREATER,
                13,
                false
        ));
        ability.setRuleAtTheTop(true);
        ability.addHint(new ValueHint("Creatures on the battlefield", new PermanentsOnBattlefieldCount(new FilterCreaturePermanent())));
        this.addAbility(ability);

        // Each player sacrifices thirteen creatures of their choice.
        Effect effect = new SacrificeAllEffect(13, StaticFilters.FILTER_PERMANENT_CREATURES)
                .setText("Each player sacrifices thirteen creatures of their choice");
        this.getSpellAbility().addEffect(effect);
    }

    private BlasphemousEdict(final BlasphemousEdict card) {
        super(card);
    }

    @Override
    public BlasphemousEdict copy() {
        return new BlasphemousEdict(this);
    }
}
