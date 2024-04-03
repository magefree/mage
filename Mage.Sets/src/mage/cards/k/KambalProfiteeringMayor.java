package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldOneOrMoreTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class KambalProfiteeringMayor extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("tokens");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public KambalProfiteeringMayor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever one or more tokens enter the battlefield under your opponents' control, for each of them, create a tapped token that's a copy of it. This ability triggers only once each turn.
        this.addAbility(new KambalProfiteeringMayorTriggeredAbility());

        // Whenever one or more tokens enter the battlefield under your control, each opponent loses 1 life and you gain 1 life.
        Ability ability = new EntersBattlefieldOneOrMoreTriggeredAbility(
                new LoseLifeOpponentsEffect(1), filter, TargetController.YOU
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private KambalProfiteeringMayor(final KambalProfiteeringMayor card) {
        super(card);
    }

    @Override
    public KambalProfiteeringMayor copy() {
        return new KambalProfiteeringMayor(this);
    }
}

class KambalProfiteeringMayorTriggeredAbility extends TriggeredAbilityImpl {

    KambalProfiteeringMayorTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
        this.setTriggersOnceEachTurn(true);
    }

    private KambalProfiteeringMayorTriggeredAbility(final KambalProfiteeringMayorTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public KambalProfiteeringMayorTriggeredAbility copy() {
        return new KambalProfiteeringMayorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_BATCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeBatchEvent zEvent = (ZoneChangeBatchEvent) event;
        Player controller = game.getPlayer(this.controllerId);
        List<UUID> tokensIds = zEvent.getEvents()
                .stream()
                .filter(zce -> zce.getToZone() == Zone.BATTLEFIELD             // keep enter the battlefield
                        && controller.hasOpponent(zce.getPlayerId(), game))   // & under your opponent's control
                .map(ZoneChangeEvent::getTarget)
                .filter(Objects::nonNull)
                .filter(p -> p instanceof PermanentToken) // collect only tokens
                .map(Permanent::getId)
                .collect(Collectors.toList());
        if (tokensIds.isEmpty()) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new KambalProfiteeringMayorEffect(tokensIds));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever one or more tokens enter the battlefield under your opponents' control, "
                + "for each of them, create a tapped token that's a copy of it. "
                + "This ability triggers only once each turn.";
    }

}

class KambalProfiteeringMayorEffect extends OneShotEffect {

    private final List<UUID> tokensIds;

    KambalProfiteeringMayorEffect(List<UUID> tokensIds) {
        super(Outcome.PutCreatureInPlay);
        this.tokensIds = new ArrayList<>(tokensIds);
    }

    private KambalProfiteeringMayorEffect(final KambalProfiteeringMayorEffect effect) {
        super(effect);
        this.tokensIds = new ArrayList<>(effect.tokensIds);
    }

    @Override
    public KambalProfiteeringMayorEffect copy() {
        return new KambalProfiteeringMayorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID tokenId : tokensIds) {
            new CreateTokenCopyTargetEffect(source.getControllerId(), null, false, 1, true, false)
                    .setTargetPointer(new FixedTarget(tokenId))
                    .apply(game, source);
        }
        return true;
    }

}