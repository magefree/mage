package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterPlayer;
import mage.filter.FilterOpponent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.*;

/**
 * @author Susucr
 */
public final class DackFaydenHelpingHand extends CardImpl {

    private static final Hint hint = new ValueHint("Number of opponents", OpponentsCount.instance);

    public DackFaydenHelpingHand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // When Dack Fayden enters, reveal cards from the top of your library until you reveal X creature cards, where X is the number of opponents you have. Put those creature cards onto the battlefield, then shuffle. They're goaded for the rest of the game. For each of those permanents, choose a different opponent. Each opponent gains control of the permanent for which they were chosen.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DackFaydenHelpingHandEffect()).addHint(hint));
    }

    private DackFaydenHelpingHand(final DackFaydenHelpingHand card) {
        super(card);
    }

    @Override
    public DackFaydenHelpingHand copy() {
        return new DackFaydenHelpingHand(this);
    }
}

class DackFaydenHelpingHandEffect extends OneShotEffect {

    DackFaydenHelpingHandEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "reveal cards from the top of your library until you reveal X creature cards, where X is the number of opponents you have. " +
                "Put those creature cards onto the battlefield, then shuffle. They're goaded for the rest of the game. " +
                "For each of those permanents, choose a different opponent. Each opponent gains control of the permanent for which they were chosen";
    }

    private DackFaydenHelpingHandEffect(final DackFaydenHelpingHandEffect effect) {
        super(effect);
    }

    @Override
    public DackFaydenHelpingHandEffect copy() {
        return new DackFaydenHelpingHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        int x = game.getOpponents(controller.getId()).size();
        Cards revealed = new CardsImpl();
        Set<Card> creatureCards = new LinkedHashSet<>();

        if (x > 0) {
            for (Card card : controller.getLibrary().getCards(game)) {
                revealed.add(card);
                if (card.isCreature(game)) {
                    creatureCards.add(card);
                    if (creatureCards.size() >= x) {
                        break;
                    }
                }
            }
            controller.revealCards(source, revealed, game);
            controller.moveCards(creatureCards, Zone.BATTLEFIELD, source, game);
        }
        controller.shuffleLibrary(source, game);

        List<Permanent> permanents = new ArrayList<>();
        for (Card card : creatureCards) {
            Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
            if (permanent != null) {
                permanents.add(permanent);
            }
        }

        for (Permanent permanent : permanents) {
            game.addEffect(new GoadTargetEffect(Duration.EndOfGame)
                    .setTargetPointer(new FixedTarget(permanent, game)), source);
        }

        Set<UUID> chosenOpponents = new HashSet<>();
        Map<Permanent, UUID> assignments = new LinkedHashMap<>();

        for (Permanent permanent : permanents) {
            FilterPlayer filter = new FilterOpponent("a different opponent to gain control of " + permanent.getName());
            for (UUID chosenOpponentId : chosenOpponents) {
                filter.add(Predicates.not(new PlayerIdPredicate(chosenOpponentId)));
            }
            TargetPlayer target = new TargetPlayer(1, 1, true, filter);
            target.withNotTarget(true);
            target.withTargetName("different opponent to gain control of " + permanent.getLogName());
            if (!target.canChoose(controller.getId(), source, game)) {
                break;
            }
            controller.choose(Outcome.Neutral, target, source, game);
            UUID chosenOpponentId = target.getFirstTarget();
            if (chosenOpponentId == null) {
                Set<UUID> possible = target.possibleTargets(controller.getId(), source, game);
                if (!possible.isEmpty()) {
                    chosenOpponentId = possible.iterator().next();
                }
            }
            if (chosenOpponentId != null) {
                chosenOpponents.add(chosenOpponentId);
                assignments.put(permanent, chosenOpponentId);
                Player chosenPlayer = game.getPlayer(chosenOpponentId);
                if (chosenPlayer != null) {
                    game.informPlayers(controller.getLogName() + " chose " + chosenPlayer.getLogName() + " for " + permanent.getLogName());
                }
            }
        }

        for (Map.Entry<Permanent, UUID> entry : assignments.entrySet()) {
            ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfGame, entry.getValue());
            effect.setTargetPointer(new FixedTarget(entry.getKey(), game));
            game.addEffect(effect, source);
        }

        return true;
    }
}
