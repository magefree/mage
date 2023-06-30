package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RamirezDePietroPillager extends CardImpl {

    public RamirezDePietroPillager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Ramirez DePietro, Pillager enters the battlefield, you lose 2 life and create two Treasure tokens.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseLifeSourceControllerEffect(2));
        ability.addEffect(new CreateTokenEffect(new TreasureToken(), 2).setText("and create two Treasure tokens"));
        this.addAbility(ability);

        // Whenever one or more Pirates you control deal combat damage to a player, exile the top card of that player's library. You may cast that card for as long as it remains exiled.
        this.addAbility(new RamirezDePietroPillagerTriggeredAbility());
    }

    private RamirezDePietroPillager(final RamirezDePietroPillager card) {
        super(card);
    }

    @Override
    public RamirezDePietroPillager copy() {
        return new RamirezDePietroPillager(this);
    }
}

class RamirezDePietroPillagerTriggeredAbility extends TriggeredAbilityImpl {

    private final List<UUID> damagedPlayerIds = new ArrayList<>();

    RamirezDePietroPillagerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RamirezDePietroPillagerEffect());
    }

    private RamirezDePietroPillagerTriggeredAbility(final RamirezDePietroPillagerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RamirezDePietroPillagerTriggeredAbility copy() {
        return new RamirezDePietroPillagerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (event.getType()) {
            case COMBAT_DAMAGE_STEP_POST:
                damagedPlayerIds.clear();
                return false;
            case DAMAGED_PLAYER:
                break;
            default:
                return false;
        }
        if (!((DamagedPlayerEvent) event).isCombatDamage()) {
            return false;
        }
        Permanent creature = game.getPermanent(event.getSourceId());
        if (creature != null
                && creature.isControlledBy(getControllerId())
                && creature.hasSubtype(SubType.PIRATE, game)
                && !damagedPlayerIds.contains(event.getTargetId())) {
            damagedPlayerIds.add(event.getTargetId());
            this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more Pirates you control deal combat damage to a player, " +
                "exile the top card of that player's library. You may cast that card for as long as it remains exiled.";
    }
}

class RamirezDePietroPillagerEffect extends OneShotEffect {

    RamirezDePietroPillagerEffect() {
        super(Outcome.Benefit);
    }

    private RamirezDePietroPillagerEffect(final RamirezDePietroPillagerEffect effect) {
        super(effect);
    }

    @Override
    public RamirezDePietroPillagerEffect copy() {
        return new RamirezDePietroPillagerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        CardUtil.makeCardPlayable(game, source, card, Duration.Custom, false, source.getControllerId(), null);
        return true;
    }
}
