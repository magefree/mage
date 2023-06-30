package mage.cards.r;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.TreasureToken;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;
import mage.watchers.common.SpellsCastWatcher;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class RashmiAndRagavan extends CardImpl {

    public RashmiAndRagavan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.MONKEY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you cast your first spell during each of your turns,
        // exile the top card of target opponent's library and create a Treasure token.
        // Then you may cast the exiled card without paying its mana cost if it's a spell with mana value
        // less than the number of artifacts you control.
        // If you don’t cast it this way, you may cast it this turn.
        this.addAbility(new RashmiAndRagavanTriggeredAbility());
    }

    private RashmiAndRagavan(final RashmiAndRagavan card) {
        super(card);
    }

    @Override
    public RashmiAndRagavan copy() {
        return new RashmiAndRagavan(this);
    }
}

class RashmiAndRagavanTriggeredAbility extends SpellCastControllerTriggeredAbility {

    RashmiAndRagavanTriggeredAbility() {
        super(new CreateTokenEffect(new TreasureToken()), false);
        this.addTarget(new TargetOpponent());
        this.addWatcher(new SpellsCastWatcher());
        this.addEffect(new RashmiAndRagavanEffect());
    }

    RashmiAndRagavanTriggeredAbility(RashmiAndRagavanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RashmiAndRagavanTriggeredAbility copy() {
        return new RashmiAndRagavanTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game) && game.isActivePlayer(event.getPlayerId())) {
            SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
            if (watcher != null) {
                List<Spell> spells = watcher.getSpellsCastThisTurn(event.getPlayerId());
                if (spells != null && spells.size() == 1) {
                    Spell spell = game.getStack().getSpell(event.getTargetId());
                    return spell != null;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast your first spell during each of your turns, exile the top card of target "
                + "opponent's library and create a Treasure token. Then you may cast the exiled card without "
                + "paying its mana cost if it's a spell with mana value less than the number of artifacts you "
                + "control. If you don't cast it this way, you may cast it this turn.";
    }
}

class RashmiAndRagavanEffect extends OneShotEffect {

    RashmiAndRagavanEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "exile the top card of target opponent's library and create a Treasure token. "
                + "Then you may cast the exiled card without paying its mana cost if it's a spell with mana value "
                + "less than the number of artifacts you control. If you don't cast it this way, "
                + "you may cast it this turn";
    }

    RashmiAndRagavanEffect(final RashmiAndRagavanEffect effect) {
        super(effect);
    }

    @Override
    public RashmiAndRagavanEffect copy() {
        return new RashmiAndRagavanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        MageObject sourceObject = source.getSourceObject(game);
        UUID exileId = CardUtil.getExileZoneId(
                controller.getId().toString()
                        + "-" + game.getState().getTurnNum()
                        + "-" + sourceObject.getIdName(), game
        );
        String exileName = sourceObject.getIdName() + " play on turn " + game.getState().getTurnNum()
                + " for " + controller.getName();
        game.getExile().createZone(exileId, exileName).setCleanupOnEndTurn(true);
        Set<Card> cards = new HashSet<>();
        cards.add(card);
        if (card == null || !controller.moveCardsToExile(cards, source, game, true, exileId, exileName)) {
            return false;
        }
        int artifactCount = new PermanentsOnBattlefieldCount(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT
        ).calculate(game, source, this);
        FilterCard filter = new FilterCard();
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, artifactCount));
        Boolean cardWasCast = CardUtil.castSpellWithAttributesForFree(controller, source, game, new CardsImpl(card), filter);
        if (!cardWasCast) {
            ContinuousEffect effect = new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, TargetController.YOU, Duration.EndOfTurn, false, true);
            effect.setTargetPointer(new FixedTargets(cards, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}
