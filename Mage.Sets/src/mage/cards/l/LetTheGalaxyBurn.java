package mage.cards.l;

import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LetTheGalaxyBurn extends CardImpl {

    private static final DynamicValue xValue = new AdditiveDynamicValue(
            ManacostVariableValue.REGULAR, StaticValue.get(2)
    );
    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.not(EnteredThisTurnPredicate.instance));
    }

    public LetTheGalaxyBurn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{5}{R}");

        // Cascade
        this.addAbility(new CascadeAbility());

        // Let the Galaxy Burn deals X plus 2 damage to each creature that didn't enter the battlefield this turn.
        this.getSpellAbility().addEffect(new DamageAllEffect(xValue, filter)
                .setText("{this} deals X plus 2 damage to each creature that didn't enter the battlefield this turn"));
    }

    private LetTheGalaxyBurn(final LetTheGalaxyBurn card) {
        super(card);
    }

    @Override
    public LetTheGalaxyBurn copy() {
        return new LetTheGalaxyBurn(this);
    }
}
