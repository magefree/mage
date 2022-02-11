
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class GoblinGuide extends CardImpl {

    public GoblinGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Whenever Goblin Guide attacks, defending player reveals the top card of their library.
        // If it's a land card, that player puts it into their hand.
        this.addAbility(new GoblinGuideTriggeredAbility(new GoblinGuideEffect(), false));
    }

    private GoblinGuide(final GoblinGuide card) {
        super(card);
    }

    @Override
    public GoblinGuide copy() {
        return new GoblinGuide(this);
    }

}

class GoblinGuideTriggeredAbility extends TriggeredAbilityImpl {

    protected String text;

    public GoblinGuideTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public GoblinGuideTriggeredAbility(Effect effect, boolean optional, String text) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.text = text;
    }

    public GoblinGuideTriggeredAbility(final GoblinGuideTriggeredAbility ability) {
        super(ability);
        this.text = ability.text;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            UUID defenderId = game.getCombat().getDefendingPlayerId(getSourceId(), game);
            if (defenderId != null) {
                for (Effect effect : this.getEffects()) {
                    // set here because attacking creature can be removed until effect resolves
                    effect.setTargetPointer(new FixedTarget(defenderId, game));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
            return "Whenever {this} attacks, defending player reveals the top card of their library. If it's a land card, that player puts it into their hand.";
    }

    @Override
    public GoblinGuideTriggeredAbility copy() {
        return new GoblinGuideTriggeredAbility(this);
    }

}

class GoblinGuideEffect extends OneShotEffect {

    public GoblinGuideEffect() {
        super(Outcome.DrawCard);
    }

    public GoblinGuideEffect(final GoblinGuideEffect effect) {
        super(effect);
    }

    @Override
    public GoblinGuideEffect copy() {
        return new GoblinGuideEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player defender = game.getPlayer(getTargetPointer().getFirst(game, source));
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null && defender != null) {
            Card card = defender.getLibrary().getFromTop(game);
            if (card != null) {
                Cards cards = new CardsImpl(card);
                defender.revealCards(sourceObject.getName(), cards, game);
                if (card.isLand(game)) {
                    defender.moveCards(card, Zone.HAND, source, game);
                }
            }
            return true;
        }
        return false;
    }

}
