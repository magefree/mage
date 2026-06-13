package mage.cards.w;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.WallColorlessToken;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class WallOff extends CardImpl {

    public WallOff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // This spell costs {1} less to cast for each creature your opponents control.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new WallOffCostReductionEffect());
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Create a 0/4 colorless Wall creature token with defender. You gain 4 life.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new WallColorlessToken()));
        this.getSpellAbility().addEffect(new GainLifeEffect(4));
    }

    private WallOff(final WallOff card) {
        super(card);
    }

    @Override
    public WallOff copy() {
        return new WallOff(this);
    }
}

class WallOffCostReductionEffect extends CostModificationEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each creature your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    WallOffCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "this spell costs {1} less to cast for each creature your opponents control";
    }

    private WallOffCostReductionEffect(final WallOffCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int reductionAmount = game.getBattlefield().count(filter, source.getControllerId(), source, game);
            CardUtil.reduceCost(abilityToModify, reductionAmount);
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((abilityToModify instanceof SpellAbility) && abilityToModify.getSourceId().equals(source.getSourceId())) {
            return game.getCard(abilityToModify.getSourceId()) != null;
        }
        return false;
    }

    @Override
    public WallOffCostReductionEffect copy() {
        return new WallOffCostReductionEffect(this);
    }
}
