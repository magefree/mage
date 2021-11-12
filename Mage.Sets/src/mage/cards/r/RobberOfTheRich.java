package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.common.AttackedThisTurnWatcher;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RobberOfTheRich extends CardImpl {

    public RobberOfTheRich(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Robber of the Rich attacks, if defending player has more cards in hand than you, exile the top card of their library. During any turn you attacked with a Rogue, you may cast that card and you may spend mana as though it were mana of any color to cast that spell.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(
                        new RobberOfTheRichEffect(), false, "", SetTargetPointer.PLAYER
                ), RobberOfTheRichAttacksCondition.instance, "Whenever {this} attacks, " +
                "if defending player has more cards in hand than you, exile the top card of their library. " +
                "During any turn you attacked with a Rogue, you may cast that card and " +
                "you may spend mana as though it were mana of any color to cast that spell."
        ).addHint(new ConditionHint(RobberOfTheRichAnyTurnAttackedCondition.instance)), new AttackedThisTurnWatcher());
    }

    private RobberOfTheRich(final RobberOfTheRich card) {
        super(card);
    }

    @Override
    public RobberOfTheRich copy() {
        return new RobberOfTheRich(this);
    }
}

enum RobberOfTheRichAttacksCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(game.getCombat().getDefendingPlayerId(source.getSourceId(), game));
        return controller != null && player != null && controller.getHand().size() < player.getHand().size();
    }
}

enum RobberOfTheRichAnyTurnAttackedCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        // your turn
        if (!source.isControlledBy(game.getActivePlayerId())) {
            return false;
        }
        // attacked with Rogue
        AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        return watcher != null && watcher.getAttackedThisTurnCreatures()
                .stream()
                .map(mor -> mor.getPermanentOrLKIBattlefield(game))
                .filter(Objects::nonNull)
                .anyMatch(permanent -> permanent.hasSubtype(SubType.ROGUE, game));
    }

    @Override
    public String toString() {
        return "During that turn you attacked with a Rogue";
    }
}

class RobberOfTheRichEffect extends OneShotEffect {

    RobberOfTheRichEffect() {
        super(Outcome.PutCreatureInPlay);
    }

    private RobberOfTheRichEffect(final RobberOfTheRichEffect effect) {
        super(effect);
    }

    @Override
    public RobberOfTheRichEffect copy() {
        return new RobberOfTheRichEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player damagedPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (controller == null || damagedPlayer == null) {
            return false;
        }
        Permanent sourceObject = source.getSourcePermanentIfItStillExists(game);
        Card card = damagedPlayer.getLibrary().getFromTop(game);
        if (card == null || sourceObject == null) {
            return false;
        }
        // move card to exile
        controller.moveCardsToExile(card, source, game, true, CardUtil.getExileZoneId(game, source), sourceObject.getIdName());
        // Add effects only if the card has a spellAbility (e.g. not for lands).
        if (card.getSpellAbility() != null) {
            // allow to cast the card
            // and you may spend mana as though it were mana of any color to cast it
            CardUtil.makeCardPlayable(game, source, card, Duration.Custom, true, null, RobberOfTheRichAnyTurnAttackedCondition.instance);
        }
        return true;
    }
}

