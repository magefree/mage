package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.OpponentLostLifeCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.FaceVillainousChoice;
import mage.choices.VillainousChoice;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.token.DalekToken;
import mage.players.Player;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.Set;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class DavrosDalekCreator extends CardImpl {

    public DavrosDalekCreator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // At the beginning of your end step, create a 3/3 black Dalek artifact creature token with menace if an opponent lost 3 or more life this turn. Then each opponent who lost 3 or more life this turn faces a villainous choice -- You draw a card, or that player discards a card.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new ConditionalOneShotEffect(
                        new CreateTokenEffect(new DalekToken()),
                        new OpponentLostLifeCondition(ComparisonType.OR_GREATER, 3)
                ),
                TargetController.YOU, false
        );
        ability.addHint(DavrosDalekCreatorHint.instance);
        ability.addEffect(new DavrosDalekCreatorEffect());

        this.addAbility(ability);
    }

    private DavrosDalekCreator(final DavrosDalekCreator card) {
        super(card);
    }

    @Override
    public DavrosDalekCreator copy() {
        return new DavrosDalekCreator(this);
    }
}

enum DavrosDalekCreatorHint implements Hint {

    instance;

    @Override
    public String getText(Game game, Ability ability) {
        if (!game.isActivePlayer(ability.getControllerId())) {
            return "";
        }

        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        String sep = "";
        String hint = "Opponents that lost 3 or more this turn: [";
        Set<UUID> opponents = game.getOpponents(ability.getControllerId());
        if (watcher != null) {
            for (UUID playerId : game.getState().getPlayersInRange(ability.getControllerId(), game)) {
                if (!opponents.contains(playerId)) {
                    continue;
                }
                int lostLive = watcher.getLifeLost(playerId);
                if (lostLive >= 3) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        hint += sep + player.getName();
                        sep = ", ";
                    }
                }
            }
        }
        return hint + "]";
    }

    @Override
    public Hint copy() {
        return instance;
    }
}


class DavrosDalekCreatorEffect extends OneShotEffect {

    private static final FaceVillainousChoice choice = new FaceVillainousChoice(
            Outcome.Detriment, new DavrosDalekCreatorFirstChoice(), new DavrosDalekCreatorSecondChoice()
    );

    DavrosDalekCreatorEffect() {
        super(Outcome.Benefit);
        staticText = "Then each opponent who lost 3 or more life this turn " + choice.generateRule();
    }

    private DavrosDalekCreatorEffect(final DavrosDalekCreatorEffect effect) {
        super(effect);
    }

    @Override
    public DavrosDalekCreatorEffect copy() {
        return new DavrosDalekCreatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        if (watcher != null) {
            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                if (!opponents.contains(playerId)) {
                    continue;
                }
                int lostLive = watcher.getLifeLost(playerId);
                if (lostLive >= 3) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        choice.faceChoice(player, game, source);
                    }
                }
            }
        }
        return true;
    }
}

class DavrosDalekCreatorFirstChoice extends VillainousChoice {
    DavrosDalekCreatorFirstChoice() {
        super("You draw a card", "{controller} draws a card");
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        return controller != null && controller.drawCards(1, source, game) > 0;
    }
}

class DavrosDalekCreatorSecondChoice extends VillainousChoice {
    DavrosDalekCreatorSecondChoice() {
        super("that player discards a card", "you discard a card");
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        return !player.discard(1, false, false, source, game).isEmpty();
    }
}
