package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author Backfir3
 */
public final class KaaliaOfTheVast extends CardImpl {

    public KaaliaOfTheVast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Kaalia of the Vast attacks an opponent, you may put an Angel, Demon, or Dragon creature card
        // from your hand onto the battlefield tapped and attacking that opponent.
        this.addAbility(new KaaliaOfTheVastAttacksAbility());
    }

    private KaaliaOfTheVast(final KaaliaOfTheVast card) {
        super(card);
    }

    @Override
    public KaaliaOfTheVast copy() {
        return new KaaliaOfTheVast(this);
    }

}

class KaaliaOfTheVastAttacksAbility extends TriggeredAbilityImpl {

    public KaaliaOfTheVastAttacksAbility() {
        super(Zone.BATTLEFIELD, new KaaliaOfTheVastEffect(), false);
    }

    public KaaliaOfTheVastAttacksAbility(final KaaliaOfTheVastAttacksAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            Player opponent = game.getPlayer(event.getTargetId());
            return opponent != null;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks an opponent, you may put an Angel, Demon, or Dragon creature card from your hand onto the battlefield tapped and attacking that opponent.";
    }

    @Override
    public KaaliaOfTheVastAttacksAbility copy() {
        return new KaaliaOfTheVastAttacksAbility(this);
    }
}

class KaaliaOfTheVastEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard("an Angel, Demon, or Dragon creature card");

    static {
        filter.add(Predicates.or(
                SubType.ANGEL.getPredicate(),
                SubType.DEMON.getPredicate(),
                SubType.DRAGON.getPredicate()));
    }

    public KaaliaOfTheVastEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "put an Angel, Demon, or Dragon creature card from your hand onto the battlefield tapped and attacking that opponent.";
    }

    public KaaliaOfTheVastEffect(final KaaliaOfTheVastEffect effect) {
        super(effect);
    }

    @Override
    public KaaliaOfTheVastEffect copy() {
        return new KaaliaOfTheVastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !controller.chooseUse(Outcome.PutCreatureInPlay, "Put an Angel, Demon, or Dragon creature card from your hand onto the battlefield tapped and attacking?", source, game)) {
            return false;
        }
        TargetCardInHand target = new TargetCardInHand(filter);
        if (target.canChoose(controller.getId(), source, game) && target.choose(outcome, controller.getId(), source.getSourceId(), source, game)) {
            if (!target.getTargets().isEmpty()) {
                UUID cardId = target.getFirstTarget();
                Card card = game.getCard(cardId);
                if (card != null && game.getCombat() != null) {
                    UUID defenderId = game.getCombat().getDefendingPlayerId(source.getSourceId(), game);
                    if (defenderId != null) {
                        controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
                        Permanent creature = game.getPermanent(cardId);
                        if (creature != null) {
                            game.getCombat().addAttackerToCombat(card.getId(), defenderId, game);
                            return true;
                        }

                    }
                }
            }
        }
        return false;
    }
}
