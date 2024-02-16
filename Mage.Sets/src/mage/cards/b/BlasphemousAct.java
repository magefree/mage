
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author nantuko
 */
public final class BlasphemousAct extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_PERMANENT_CREATURE);
    private static final Hint hint = new ValueHint("Creatures on the battlefield", xValue);

    public BlasphemousAct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{8}{R}");

        // Blasphemous Act costs {1} less to cast for each creature on the battlefield.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SpellCostReductionSourceEffect(xValue).setText("this spell costs {1} less to cast for each creature on the battlefield")
        ).addHint(hint).setRuleAtTheTop(true));

        // Blasphemous Act deals 13 damage to each creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(13, new FilterCreaturePermanent()));
    }

    private BlasphemousAct(final BlasphemousAct card) {
        super(card);
    }

    @Override
    public BlasphemousAct copy() {
        return new BlasphemousAct(this);
    }
}
