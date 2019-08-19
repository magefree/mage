
package mage.cards.k;

import java.util.UUID;
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
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
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

    public KaaliaOfTheVast(final KaaliaOfTheVast card) {
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
        return event.getType() == EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            Player opponent = game.getPlayer(event.getTargetId());
            if (opponent != null) {
                return true;
            }
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
                new SubtypePredicate(SubType.ANGEL),
                new SubtypePredicate(SubType.DEMON),
                new SubtypePredicate(SubType.DRAGON)));
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
        if (target.hasPossibleChoices(controller.getId(), game) && target.choose(getOutcome(), controller.getId(), source.getSourceId(), game)) {
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
