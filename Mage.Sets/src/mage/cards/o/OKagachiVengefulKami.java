
package mage.cards.o;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.watchers.common.PlayersAttackedLastTurnWatcher;

/**
 *
 * @author spjspj
 */
public final class OKagachiVengefulKami extends CardImpl {

    public OKagachiVengefulKami(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}{R}{G}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever O-Kagachi, Vengeful Kami deals combat damage to a player, if that player attacked you during their last turn, exile target nonland permanent that player controls
        OKagachiVengefulKamiTriggeredAbility ability = new OKagachiVengefulKamiTriggeredAbility();
        ability.addWatcher(new PlayersAttackedLastTurnWatcher());
        this.addAbility(ability);
    }

    public OKagachiVengefulKami(final OKagachiVengefulKami card) {
        super(card);
    }

    @Override
    public OKagachiVengefulKami copy() {
        return new OKagachiVengefulKami(this);
    }
}

class OKagachiVengefulKamiTriggeredAbility extends TriggeredAbilityImpl {

    private boolean madeDamge = false;
    private Set<UUID> damagedPlayers = new HashSet<>();

    public OKagachiVengefulKamiTriggeredAbility() {
        super(Zone.BATTLEFIELD, new OKagachiVengefulKamiEffect(), false);
    }

    public OKagachiVengefulKamiTriggeredAbility(final OKagachiVengefulKamiTriggeredAbility ability) {
        super(ability);
        this.madeDamge = ability.madeDamge;
        this.damagedPlayers = new HashSet<>();
        this.damagedPlayers.addAll(ability.damagedPlayers);
    }

    @Override
    public OKagachiVengefulKamiTriggeredAbility copy() {
        return new OKagachiVengefulKamiTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.DAMAGED_PLAYER) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
            UUID damagedPlayerId = game.getCombat().getDefenderId(sourceId);
            UUID you = this.getControllerId();
            Permanent p = game.getPermanent(event.getSourceId());
            if (damageEvent.isCombatDamage() && p != null && p.getId().equals(this.getSourceId())) {
                PlayersAttackedLastTurnWatcher watcher = game.getState().getWatcher(PlayersAttackedLastTurnWatcher.class);
                if (watcher != null && watcher.attackedLastTurn(damagedPlayerId, you)) {
                    FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanent defending player controls");
                    filter.add(new ControllerIdPredicate(damagedPlayerId));
                    this.getTargets().clear();
                    TargetPermanent target = new TargetPermanent(filter);
                    this.addTarget(target);

                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, if that player attacked you during their last turn, exile target nonland permanent that player controls";
    }
}

class OKagachiVengefulKamiEffect extends OneShotEffect {

    public OKagachiVengefulKamiEffect() {
        super(Outcome.Benefit);
        this.staticText = "if that player attacked you during their last turn, exile target nonland permanent that player controls";
    }

    public OKagachiVengefulKamiEffect(final OKagachiVengefulKamiEffect effect) {
        super(effect);
    }

    @Override
    public OKagachiVengefulKamiEffect copy() {
        return new OKagachiVengefulKamiEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            return new ExileTargetEffect().apply(game, source);
        }
        return false;
    }
}
