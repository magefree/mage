package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.CyclingDiscardCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class NewPerspectives extends CardImpl {

    public NewPerspectives(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{U}");

        // When New Perspectives enters the battlefield, draw three cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(3)));

        // As long as you have seven or more cards in hand, you may pay {0} rather than pay cycling costs.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NewPerspectivesCostModificationEffect()));
    }

    private NewPerspectives(final NewPerspectives card) {
        super(card);
    }

    @Override
    public NewPerspectives copy() {
        return new NewPerspectives(this);
    }
}

class NewPerspectivesCostModificationEffect extends CostModificationEffectImpl {

    NewPerspectivesCostModificationEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.SET_COST);
        this.staticText = "As long as you have seven or more cards in hand, you may pay {0} rather than pay cycling costs";
    }

    private NewPerspectivesCostModificationEffect(final NewPerspectivesCostModificationEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        Player controller = game.getPlayer(abilityToModify.getControllerId());
        return controller != null
                && controller.getId().equals(source.getControllerId())
                && abilityToModify instanceof CyclingAbility
                && controller.getHand().size() >= 7;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(abilityToModify.getControllerId());
        if (controller != null) {
            if (game.inCheckPlayableState()
                    || controller.chooseUse(Outcome.PlayForFree, "Pay {0} to cycle?", source, game)) {
                abilityToModify.clearCosts();
                abilityToModify.clearManaCostsToPay();
                abilityToModify.addCost(new CyclingDiscardCost());
            }
            return true;
        }
        return false;
    }

    @Override
    public NewPerspectivesCostModificationEffect copy() {
        return new NewPerspectivesCostModificationEffect(this);
    }
}
