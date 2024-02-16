package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ControlYourCommanderCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConvergenceOfDominion extends CardImpl {

    public ConvergenceOfDominion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Dynastic Command Node -- As long as you control your commander, activated abilities of cards in your graveyard cost {2} less to activate. This effect can't reduce the mana in that ability's activation cost to less than one mana.
        this.addAbility(new SimpleStaticAbility(new ConvergenceOfDominionEffect()).withFlavorWord("Dynastic Command Node"));

        // Translocation Protocols -- {3}, {T}: Mill three cards.
        Ability ability = new SimpleActivatedAbility(new MillCardsControllerEffect(3), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability.withFlavorWord("Translocation Protocols"));
    }

    private ConvergenceOfDominion(final ConvergenceOfDominion card) {
        super(card);
    }

    @Override
    public ConvergenceOfDominion copy() {
        return new ConvergenceOfDominion(this);
    }
}

class ConvergenceOfDominionEffect extends CostModificationEffectImpl {

    ConvergenceOfDominionEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "as long as you control your commander, activated abilities of cards " +
                "in your graveyard cost {2} less to activate. This effect can't reduce the " +
                "mana in that ability's activation cost to less than one mana";
    }

    private ConvergenceOfDominionEffect(final ConvergenceOfDominionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(abilityToModify.getControllerId());
        if (controller == null) {
            return false;
        }
        int reduceMax = CardUtil.calculateActualPossibleGenericManaReduction(
                abilityToModify.getManaCostsToPay().getMana(), 2, 1
        );
        if (reduceMax > 0) {
            CardUtil.reduceCost(abilityToModify, reduceMax);
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return ControlYourCommanderCondition.instance.apply(game, source)
                && abilityToModify instanceof ActivatedAbility
                && game.getState().getZone(abilityToModify.getSourceId()) == Zone.GRAVEYARD;
    }

    @Override
    public ConvergenceOfDominionEffect copy() {
        return new ConvergenceOfDominionEffect(this);
    }
}
