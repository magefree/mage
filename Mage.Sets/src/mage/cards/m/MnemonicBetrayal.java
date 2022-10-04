package mage.cards.m;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class MnemonicBetrayal extends CardImpl {

    public MnemonicBetrayal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{B}");

        // Exile all cards from all opponents' graveyards. You may cast those cards 
        // this turn, and you may spend mana as though it were mana of any type to cast 
        // those spells. At the beginning of the next end step, if any of those 
        // cards remain exiled, return them to their owner's graveyards.
        this.getSpellAbility().addEffect(new MnemonicBetrayalExileEffect());

        // Exile Mnemonic Betrayal.
        this.getSpellAbility().addEffect(new ExileSpellEffect().concatBy("<br>"));
    }

    private MnemonicBetrayal(final MnemonicBetrayal card) {
        super(card);
    }

    @Override
    public MnemonicBetrayal copy() {
        return new MnemonicBetrayal(this);
    }
}

class MnemonicBetrayalExileEffect extends OneShotEffect {

    public MnemonicBetrayalExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile all opponents' graveyards. " +
                "You may cast spells from among those cards this turn, " +
                "and you may spend mana as though it were mana of any type to cast those spells. " +
                "At the beginning of the next end step, if any of those cards remain exiled, " +
                "return them to their owners' graveyards";
    }

    public MnemonicBetrayalExileEffect(final MnemonicBetrayalExileEffect effect) {
        super(effect);
    }

    @Override
    public MnemonicBetrayalExileEffect copy() {
        return new MnemonicBetrayalExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        game.getOpponents(source.getControllerId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .map(g -> g.getCards(game))
                .forEach(cards::addAllCards);
        controller.moveCardsToExile(
                cards.getCards(game), source, game, true,
                source.getSourceId(), CardUtil.getSourceName(game, source)
        );
        for (Card card : cards.getCards(game)) {
            if (card.isLand(game)) {
                continue;
            }
            CardUtil.makeCardPlayable(game, source, card, Duration.EndOfTurn, true);
        }
        cards.retainZone(Zone.EXILED, game);
        game.addDelayedTriggeredAbility(new MnemonicBetrayalDelayedTriggeredAbility(
                cards.stream()
                        .map(uuid -> new MageObjectReference(uuid, game))
                        .collect(Collectors.toSet())
        ), source);
        return true;
    }
}

class MnemonicBetrayalDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final Set<MageObjectReference> morSet = new HashSet<>();

    public MnemonicBetrayalDelayedTriggeredAbility(Set<MageObjectReference> morSet) {
        super(new MnemonicBetrayalReturnEffect(morSet));
        this.triggerOnlyOnce = true;
        this.morSet.addAll(morSet);
    }

    public MnemonicBetrayalDelayedTriggeredAbility(final MnemonicBetrayalDelayedTriggeredAbility ability) {
        super(ability);
        this.morSet.addAll(ability.morSet);
    }

    @Override
    public MnemonicBetrayalDelayedTriggeredAbility copy() {
        return new MnemonicBetrayalDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_TURN_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return morSet.stream().map(mor -> mor.getCard(game)).anyMatch(Objects::nonNull);
    }

    @Override
    public String getRule() {
        return "At the beginning of the next end step, "
                + "if any of those cards remain exiled, "
                + "return them to their owner's graveyards.";
    }
}

class MnemonicBetrayalReturnEffect extends OneShotEffect {

    private final Set<MageObjectReference> morSet = new HashSet<>();

    public MnemonicBetrayalReturnEffect(Set<MageObjectReference> morSet) {
        super(Outcome.Benefit);
        this.morSet.addAll(morSet);
    }

    public MnemonicBetrayalReturnEffect(final MnemonicBetrayalReturnEffect effect) {
        super(effect);
        this.morSet.addAll(effect.morSet);
    }

    @Override
    public MnemonicBetrayalReturnEffect copy() {
        return new MnemonicBetrayalReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        return player != null && player.moveCards(new CardsImpl(
                morSet.stream()
                        .map(mor -> mor.getCard(game))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
        ), Zone.GRAVEYARD, source, game);
    }
}
