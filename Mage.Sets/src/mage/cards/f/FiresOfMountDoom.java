package mage.cards.f;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyAllAttachedToTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class FiresOfMountDoom extends CardImpl {

    public FiresOfMountDoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);

        // When Fires of Mount Doom enters the battlefield, it deals 2 damage to target creature
        // an opponent controls. Destroy all Equipment attached to that creature.
        TriggeredAbility trigger = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(2));
        trigger.addEffect(
            new DestroyAllAttachedToTargetEffect(
                StaticFilters.FILTER_PERMANENT_EQUIPMENT,
                "that creature")
        );
        trigger.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(trigger);

        // {2}{R}: Exile the top card of your library. You may play that card this turn.
        // When you play a card this way, Fires of Mount Doom deals 2 damage to each player.
        this.addAbility(new SimpleActivatedAbility(
            Zone.BATTLEFIELD,
            new FiresOfMountDoomEffect(),
            new ManaCostsImpl<>("{2}{R}")));
    }

    private FiresOfMountDoom(final FiresOfMountDoom card) {
        super(card);
    }

    @Override
    public FiresOfMountDoom copy() {
        return new FiresOfMountDoom(this);
    }
}

class FiresOfMountDoomEffect extends OneShotEffect {

    public FiresOfMountDoomEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile the top card of your library. You may play that card this turn. " +
            "When you play a card this way, Fires of Mount Doom deals 2 damage to each player";
    }

    public FiresOfMountDoomEffect(final FiresOfMountDoomEffect effect) {
        super(effect);
    }

    @Override
    public FiresOfMountDoomEffect copy() {
        return new FiresOfMountDoomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if(controller == null || sourceObject == null){
            return false;
        }

        Card card = controller.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }

        controller.moveCardsToExile(card, source, game, true, source.getSourceId(), sourceObject.getIdName());
        CardUtil.makeCardPlayable(game, source, card, Duration.EndOfTurn, false);
        game.addDelayedTriggeredAbility(new FiresOfMountDoomDelayedTriggeredAbility(card.getId()), source);
        return true;
    }
}

// TODO: this is not quite right in corner cases.
//       Inspired by Havengul Lich which I feel has similar problems.
//       For instance what if the card is [[Squee, the Immortal]] and
//       is cast with squee AsThought.
//       Or if the card is played with the AsThought, then replayed
//       during the same turn. With current code, that would trigger
//       incorrectly again.
//       Is mor the solution there? Or having a way to get the specific
//       used AsThought and having a way to identify that it was the
//       same one as the FiresOfMountDoomEffect makeCardPlayable.
class FiresOfMountDoomDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private UUID cardId;

    public FiresOfMountDoomDelayedTriggeredAbility(UUID cardId) {
        super(new DamagePlayersEffect(2, TargetController.ANY), Duration.EndOfTurn);
        this.cardId = cardId;
    }

    public FiresOfMountDoomDelayedTriggeredAbility(FiresOfMountDoomDelayedTriggeredAbility ability) {
        super(ability);
        this.cardId = ability.cardId;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        GameEvent.EventType type = event.getType();
        return type == GameEvent.EventType.PLAY_LAND
            || type == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(cardId);
    }

    @Override
    public FiresOfMountDoomDelayedTriggeredAbility copy() {
        return new FiresOfMountDoomDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When you play a card this way, {this} deals 2 damage to each player";
    }
}