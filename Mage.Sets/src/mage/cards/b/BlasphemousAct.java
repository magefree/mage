
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
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

    public BlasphemousAct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{8}{R}");

        // Blasphemous Act costs {1} less to cast for each creature on the battlefield.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new BlasphemousCostReductionEffect());
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

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

class BlasphemousCostReductionEffect extends CostModificationEffectImpl {

    BlasphemousCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "this spell costs {1} less to cast for each creature on the battlefield";
    }

    BlasphemousCostReductionEffect(BlasphemousCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) { return false; }

        int reductionAmount = game.getBattlefield().count(StaticFilters.FILTER_PERMANENT_CREATURE, source.getSourceId(), source, game);
        CardUtil.reduceCost(abilityToModify, reductionAmount);

        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((abilityToModify instanceof SpellAbility) && abilityToModify.getSourceId().equals(source.getSourceId())) {
            return game.getCard(abilityToModify.getSourceId()) != null;
        }
        return false;
    }

    @Override
    public BlasphemousCostReductionEffect copy() {
        return new BlasphemousCostReductionEffect(this);
    }
}
