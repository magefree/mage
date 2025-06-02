package mage.cards.n;

import java.util.*;
import java.util.stream.Collectors;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.GoblinToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author Jmlundeen
 */
public final class NerivCracklingVanguard extends CardImpl {

    public NerivCracklingVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Neriv enters, create two 1/1 red Goblin creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GoblinToken(false), 2)));

        // Whenever Neriv attacks, exile a number of cards from the top of your library equal to the number of differently named tokens you control. During any turn you attacked with a commander, you may play those cards.
        this.addAbility(new AttacksTriggeredAbility(new NerivCracklingVanguardEffect()));
    }

    private NerivCracklingVanguard(final NerivCracklingVanguard card) {
        super(card);
    }

    @Override
    public NerivCracklingVanguard copy() {
        return new NerivCracklingVanguard(this);
    }
}

class NerivCracklingVanguardEffect extends OneShotEffect {

    NerivCracklingVanguardEffect() {
        super(Outcome.Benefit);
        staticText = "exile a number of cards from the top of your library equal to the number of " +
                "differently named tokens you control. During any turn you attacked with a commander, you may play those cards.";
    }

    private NerivCracklingVanguardEffect(final NerivCracklingVanguardEffect effect) {
        super(effect);
    }

    @Override
    public NerivCracklingVanguardEffect copy() {
        return new NerivCracklingVanguardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        int tokenNameCount = NerivDynamicValue.instance.calculate(game, source, this);
        if (controller == null || tokenNameCount == 0) {
            return false;
        }
        Set<Card> cards = controller.getLibrary().getTopCards(game, tokenNameCount);
        if (cards.isEmpty()) {
            return false;
        }
        controller.moveCardsToExile(cards, source, game, true, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source));
        // copy ability in case Neriv leaves the battlefield. Card should be playable any turn you've attacked with a commander.
        Ability copiedAbility = source.copy();
        copiedAbility.newId();
        copiedAbility.setControllerId(source.getControllerId());
        PlayFromNotOwnHandZoneTargetEffect playFromExile = new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, Duration.EndOfGame);
        playFromExile.setTargetPointer(new FixedTargets(cards, game));
        ConditionalAsThoughEffect playOnlyIfAttackedWithCommander = new ConditionalAsThoughEffect(playFromExile, new CommanderAttackedThisTurnCondition(copiedAbility));
        playOnlyIfAttackedWithCommander.setTargetPointer(new FixedTargets(cards, game));
        game.addEffect(playOnlyIfAttackedWithCommander, copiedAbility);
        return true;
    }
}

enum NerivDynamicValue implements DynamicValue {
    instance;


    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return (int) game.getBattlefield().getAllActivePermanents(sourceAbility.getControllerId()).stream()
                .filter(permanent -> permanent instanceof PermanentToken)
                .map(MageObject::getName)
                .distinct()
                .count();
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "X";
    }
}

class CommanderAttackedThisTurnCondition implements Condition {

    private Ability ability;

    CommanderAttackedThisTurnCondition(Ability source) {
        this.ability = source;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // in case Neriv leaves the battlefield, the ability must be referenced for controller information
        if (ability == null) {
            ability = source;
        }
        // your turn
        if (!ability.isControlledBy(game.getActivePlayerId())) {
            return false;
        }
        Player controller = game.getPlayer(ability.getControllerId());
        if (controller == null) {
            return false;
        }
        // attacked with Commander
        // note that the MOR object doesn't work well with LKI call when checking for the subtype, thus we check the LKI permanent in the battlefield
        Set<UUID> commanderIds = game.getPlayerList()
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(player -> game.getCommandersIds(player, CommanderCardType.COMMANDER_OR_OATHBREAKER, true))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        return watcher != null && watcher.getAttackedThisTurnCreaturesPermanentLKI()
                .stream()
                .filter(Objects::nonNull)
                .anyMatch(permanent -> commanderIds.contains(permanent.getId()));
    }

    @Override
    public String toString() {
        return "During that turn you attacked with a Commander";
    }
}