package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SkipDrawStepEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.replacement.GraveyardFromAnywhereExileReplacementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class Necrodominance extends CardImpl {

    public Necrodominance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);

        // Skip your draw step.
        this.addAbility(new SimpleStaticAbility(new SkipDrawStepEffect()));

        // At the beginning of your end step, you may pay any amount of life. If you do, draw that many cards.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new NecrodominanceEffect()
        ));

        // Your maximum hand size is five.
        this.addAbility(new SimpleStaticAbility(
                new MaximumHandSizeControllerEffect(
                        5, Duration.WhileOnBattlefield,
                        MaximumHandSizeControllerEffect.HandSizeModification.SET,
                        TargetController.YOU
                )
        ));

        // If a card or token would be put into your graveyard from anywhere, exile it instead.
        this.addAbility(new SimpleStaticAbility(new GraveyardFromAnywhereExileReplacementEffect(true, true)));
    }

    private Necrodominance(final Necrodominance card) {
        super(card);
    }

    @Override
    public Necrodominance copy() {
        return new Necrodominance(this);
    }
}

// Inspired by Phyrexian Processor
class NecrodominanceEffect extends OneShotEffect {

    NecrodominanceEffect() {
        super(Outcome.LoseLife);
        staticText = "you may pay any amount of life. If you do, draw that many cards";
    }

    private NecrodominanceEffect(final NecrodominanceEffect effect) {
        super(effect);
    }

    @Override
    public NecrodominanceEffect copy() {
        return new NecrodominanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int payAmount = controller.getAmount(0, controller.getLife(), "Pay any amount of life", source, game);
        Cost cost = new PayLifeCost(payAmount);
        if (!cost.pay(source, game, source, source.getControllerId(), true)) {
            return false;
        }
        controller.drawCards(payAmount, source, game);
        return true;
    }

}
