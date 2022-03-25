package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElderBrain extends CardImpl {

    public ElderBrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Elder Brain attacks a player, exile all cards from that player's hand, then they draw that many cards. You may play lands and cast spells from among the exiled cards for as long as they remain exiled. If you cast a spell this way, you may spend mana as though it were mana of any color to cast it.
        this.addAbility(new ElderBrainTriggeredAbility());
    }

    private ElderBrain(final ElderBrain card) {
        super(card);
    }

    @Override
    public ElderBrain copy() {
        return new ElderBrain(this);
    }
}

class ElderBrainTriggeredAbility extends TriggeredAbilityImpl {

    ElderBrainTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ElderBrainEffect());
    }

    private ElderBrainTriggeredAbility(final ElderBrainTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ElderBrainTriggeredAbility copy() {
        return new ElderBrainTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player player = game.getPlayer(game.getCombat().getDefenderId(this.getSourceId()));
        if (player == null) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(player.getId()));
        return true;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever {this} attacks a player, ";
    }
}

class ElderBrainEffect extends OneShotEffect {

    ElderBrainEffect() {
        super(Outcome.Benefit);
        staticText = "exile all cards from that player's hand, then they draw that many cards. " +
                "You may play lands and cast spells from among the exiled cards for as long as they remain exiled. " +
                "If you cast a spell this way, you may spend mana as though it were mana of any color to cast it";
    }

    private ElderBrainEffect(final ElderBrainEffect effect) {
        super(effect);
    }

    @Override
    public ElderBrainEffect copy() {
        return new ElderBrainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || player == null || player.getHand().isEmpty()) {
            return false;
        }
        Cards cards = new CardsImpl(player.getHand());
        controller.moveCards(cards, Zone.EXILED, source, game);
        player.drawCards(cards.size(), source, game);
        for (Card card : cards.getCards(game)) {
            CardUtil.makeCardPlayable(
                    game, source, card, Duration.Custom,
                    true, controller.getId(), null
            );
        }
        return true;
    }
}
