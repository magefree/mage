package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.permanent.token.StoneTrapIdolToken;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class StoneIdolTrap extends CardImpl {

    public StoneIdolTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{R}");
        this.subtype.add(SubType.TRAP);

        // Stone Idol Trap costs {1} less to cast for each attacking creature.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new StoneIdolTrapCostReductionEffect());
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Create a 6/12 colorless Construct artifact creature token with trample. Exile it at the beginning of your next end step.
        this.getSpellAbility().addEffect(new StoneIdolTrapEffect());
    }

    private StoneIdolTrap(final StoneIdolTrap card) {
        super(card);
    }

    @Override
    public StoneIdolTrap copy() {
        return new StoneIdolTrap(this);
    }
}

class StoneIdolTrapCostReductionEffect extends CostModificationEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(AttackingPredicate.instance);
    }

    public StoneIdolTrapCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "This spell costs {1} less to cast for each attacking creature";
    }

    protected StoneIdolTrapCostReductionEffect(StoneIdolTrapCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int reductionAmount = game.getBattlefield().count(filter, source.getControllerId(), source, game);
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
    public StoneIdolTrapCostReductionEffect copy() {
        return new StoneIdolTrapCostReductionEffect(this);
    }
}

class StoneIdolTrapEffect extends OneShotEffect {

    public StoneIdolTrapEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a 6/12 colorless Construct artifact creature token with trample. Exile it at the beginning of your next end step";
    }

    public StoneIdolTrapEffect(final StoneIdolTrapEffect effect) {
        super(effect);
    }

    @Override
    public StoneIdolTrapEffect copy() {
        return new StoneIdolTrapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenEffect effect = new CreateTokenEffect(new StoneTrapIdolToken());
        if (effect.apply(game, source)) {
            effect.exileTokensCreatedAtNextEndStep(game, source);
            return true;
        }
        return false;
    }
}
