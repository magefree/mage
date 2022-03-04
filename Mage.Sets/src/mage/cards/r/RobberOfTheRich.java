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
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.common.AttackedThisTurnWatcher;
import java.util.Objects;
import java.util.UUID;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.common.asthought.YouMaySpendManaAsAnyColorToCastTargetEffect;
import mage.target.targetpointer.FixedTarget;

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
        ).addHint(new ConditionHint(new RogueAttackedThisTurnCondition(null))), new AttackedThisTurnWatcher());
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
        return controller != null 
                && player != null 
                && controller.getHand().size() < player.getHand().size();
    }
}

class RogueAttackedThisTurnCondition implements Condition {
 
    private Ability ability;
    
    RogueAttackedThisTurnCondition(Ability source) {
        this.ability = source;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // in case the Robber leaves the battlefield, the ability must be referenced for controller information
        if (ability == null) {
            ability = source;
        }
        // your turn
        if (!ability.isControlledBy(game.getActivePlayerId())) {
            return false;
        }
        // attacked with Rogue
        // note that the MOR object doesn't work well with LKI call when checking for the subtype, thus we check the LKI permanent in the battlefield
        AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        return watcher != null && watcher.getAttackedThisTurnCreaturesPermanentLKI()
                .stream()
                .map(permanent -> permanent)
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
        super(Outcome.Benefit);
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
        if (controller == null 
                || damagedPlayer == null) {
            return false;
        }
        Card card = damagedPlayer.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        // move card to exile
        controller.moveCardsToExile(card, source, game, true, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source));
        // add the effects to the exiled card directly
        // don't worry about land
        if (card.getSpellAbility() != null) {
            // the exiled card is independent and requires a new ability in case the Robber leaves the battlefield
            // the exiled card can be cast throughout the entire game as long as the controller attacked with a rogue that turn
            Ability copiedAbility = source.copy();
            copiedAbility.newId();
            copiedAbility.setSourceId(card.getId());
            copiedAbility.setControllerId(source.getControllerId());
            PlayFromNotOwnHandZoneTargetEffect playFromExile = new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, Duration.EndOfGame);
            YouMaySpendManaAsAnyColorToCastTargetEffect spendAnyMana = new YouMaySpendManaAsAnyColorToCastTargetEffect(Duration.EndOfGame);
            ConditionalAsThoughEffect castOnlyIfARogueAttackedThisTurn = new ConditionalAsThoughEffect(playFromExile, new RogueAttackedThisTurnCondition(copiedAbility));
            playFromExile.setTargetPointer(new FixedTarget(card, game));
            spendAnyMana.setTargetPointer(new FixedTarget(card, game));
            castOnlyIfARogueAttackedThisTurn.setTargetPointer(new FixedTarget(card, game));
            game.addEffect(castOnlyIfARogueAttackedThisTurn, copiedAbility);
            game.addEffect(spendAnyMana, copiedAbility);
            return true;
        }
        return false;
    }
}

