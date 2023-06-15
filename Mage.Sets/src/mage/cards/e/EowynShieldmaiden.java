package mage.cards.e;

import java.util.*;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.*;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.HumanKnightToken;
import mage.watchers.Watcher;

/**
 *
 * @author Susucr
 */
public final class EowynShieldmaiden extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.HUMAN, "Humans");

    public EowynShieldmaiden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // At the beginning of combat on your turn,
        // if another Human entered the battlefield under your control this turn,
        // create two 2/2 red Human Knight creature tokens with trample and haste.
        // Then if you control six or more Humans, draw a card.

        TriggeredAbility triggeredAbility = new BeginningOfCombatTriggeredAbility(
            Zone.BATTLEFIELD,
            new CreateTokenEffect(new HumanKnightToken(), 2),
            TargetController.YOU, false, false
        );
        triggeredAbility.addEffect(new ConditionalOneShotEffect(
            new DrawCardSourceControllerEffect(1),
            new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 5)
        ));

        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
            triggeredAbility,
            EowynShieldmaidenCondition.instance,
            "At the beginning of combat on your turn, "
            + "if another Human entered the battlefield "
            + "under your control this turn, create two "
            + "2/2 red Human Knight creature tokens with "
            + "trample and haste."
        ), new EowynShieldmaidenWatcher());
    }

    private EowynShieldmaiden(final EowynShieldmaiden card) {
        super(card);
    }

    @Override
    public EowynShieldmaiden copy() {
        return new EowynShieldmaiden(this);
    }
}

enum EowynShieldmaidenCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        EowynShieldmaidenWatcher watcher = game.getState().getWatcher(EowynShieldmaidenWatcher.class);
        return watcher != null
            && watcher.hasPlayerHadAnotherHumanEnterThisTurn(
                source.getSourcePermanentOrLKI(game),
                source.getControllerId());
    }
}

class EowynShieldmaidenWatcher extends Watcher {

    // Map players UUID to theirs humans UUID that entered this turn.
    private final Map<UUID, Set<UUID>> humanEnterings = new HashMap<>();

    EowynShieldmaidenWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {

            // Is the thing entering an human?
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent == null || !permanent.hasSubtype(SubType.HUMAN, game)) {
                return;
            }

            // Then, stores the info about that event for latter.
            UUID playerId = event.getPlayerId();
            Set<UUID> setForThatPlayer = this.humanEnterings.getOrDefault(playerId, new HashSet<>());
            setForThatPlayer.add(permanent.getId());
            this.humanEnterings.put(playerId, setForThatPlayer);
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.humanEnterings.clear();
    }

    boolean hasPlayerHadAnotherHumanEnterThisTurn(Permanent sourcePermanent, UUID playerId) {
        // The trick there is that we need to exclude the sourcePermanent of the trigger (Eowyn most likely),
        //     from the humans that entered the battlefield this turn under the player.
        UUID sourceId = null;
        if(sourcePermanent != null)
            sourceId = sourcePermanent.getId();

        UUID finalSourceId = sourceId;
        Set<UUID> setForThePlayer = this.humanEnterings.getOrDefault(playerId, new HashSet<>());
        return setForThePlayer.stream().anyMatch(
            humanUUID -> finalSourceId == null || humanUUID != finalSourceId
        );
    }
}
