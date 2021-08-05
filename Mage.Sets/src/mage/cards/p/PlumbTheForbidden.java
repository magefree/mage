package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CopySourceSpellEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PlumbTheForbidden extends CardImpl {

    public PlumbTheForbidden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // As an additional cost to cast this spell, you may sacrifice one or more creatures. When you do, copy this spell for each creature sacrificed this way.
        this.getSpellAbility().addCost(new PlumbTheForbiddenCost());

        // You draw a card and you lose 1 life.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).setText("you draw a card"));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
    }

    private PlumbTheForbidden(final PlumbTheForbidden card) {
        super(card);
    }

    @Override
    public PlumbTheForbidden copy() {
        return new PlumbTheForbidden(this);
    }
}

class PlumbTheForbiddenCost extends SacrificeTargetCost {

    PlumbTheForbiddenCost() {
        super(new TargetControlledPermanent(0, Integer.MAX_VALUE, StaticFilters.FILTER_CONTROLLED_CREATURES, true));
        this.text = "you may sacrifice one or more creatures. When you do, " +
                "copy this spell for each creature sacrificed this way";
    }

    private PlumbTheForbiddenCost(final PlumbTheForbiddenCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        if (!super.pay(ability, game, source, controllerId, noMana, costToPay)) {
            return false;
        }
        int sacrificed = getPermanents().size();
        if (sacrificed > 0) {
            game.fireReflexiveTriggeredAbility(new ReflexiveTriggeredAbility(
                    new CopySourceSpellEffect(sacrificed), false, "when you do, " +
                    "copy this spell for each creature sacrificed this way"
            ), source);
        }
        return true;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public PlumbTheForbiddenCost copy() {
        return new PlumbTheForbiddenCost(this);
    }
}
