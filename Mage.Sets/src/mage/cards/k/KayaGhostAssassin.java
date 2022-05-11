package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.delayed.AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class KayaGhostAssassin extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("target creature to exile. Choose no targets to exile Kaya.");

    public KayaGhostAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{B}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KAYA);

        this.setStartingLoyalty(5);

        // 0: Exile Kaya, Ghost Assassin or up to one target creature. Return that card to the battlefield under its owner's control at the beginning of your next upkeep.
        // You lose 2 life.
        Ability ability = new LoyaltyAbility(new KayaGhostAssassinEffect(), 0);
        ability.addTarget(new TargetCreaturePermanent(0, 1, filter, false));
        this.addAbility(ability);

        // -1: Each opponent loses 2 life and you gain 2 life.
        ability = new LoyaltyAbility(new LoseLifeOpponentsEffect(2), -1);
        Effect effect = new GainLifeEffect(2);
        effect.setText("and you gain 2 life");
        ability.addEffect(effect);
        this.addAbility(ability);

        // -2: Each opponent discards a card and you draw a card.
        ability = new LoyaltyAbility(new DiscardEachPlayerEffect(TargetController.OPPONENT), -2);
        effect = new DrawCardSourceControllerEffect(1);
        effect.setText("and you draw a card");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private KayaGhostAssassin(final KayaGhostAssassin card) {
        super(card);
    }

    @Override
    public KayaGhostAssassin copy() {
        return new KayaGhostAssassin(this);
    }
}

class KayaGhostAssassinEffect extends OneShotEffect {

    public KayaGhostAssassinEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile {this} or up to one target creature. Return that card to the battlefield under its owner's control at the beginning of your next upkeep. "
                + "You lose 2 life";
    }

    public KayaGhostAssassinEffect(final KayaGhostAssassinEffect effect) {
        super(effect);
    }

    @Override
    public KayaGhostAssassinEffect copy() {
        return new KayaGhostAssassinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            if (getTargetPointer().getFirst(game, source) != null) {
                Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
                if (targetCreature != null) {
                    int zcc = targetCreature.getZoneChangeCounter(game);
                    if (controller.moveCards(targetCreature, Zone.EXILED, source, game)) {
                        Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
                        effect.setTargetPointer(new FixedTarget(targetCreature.getId(), zcc + 1));
                        AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility delayedAbility
                                = new AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility(effect);
                        game.addDelayedTriggeredAbility(delayedAbility, source);
                    }
                }
            } else {
                int zcc = sourcePermanent.getZoneChangeCounter(game);
                if (controller.moveCards(sourcePermanent, Zone.EXILED, source, game)) {
                    Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
                    effect.setTargetPointer(new FixedTarget(sourcePermanent.getId(), zcc + 1));
                    AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility delayedAbility
                            = new AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility(effect);
                    game.addDelayedTriggeredAbility(delayedAbility, source);
                }
            }
            controller.loseLife(2, game, source, false);
            return true;
        }
        return false;
    }
}
