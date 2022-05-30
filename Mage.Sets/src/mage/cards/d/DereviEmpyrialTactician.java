package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class DereviEmpyrialTactician extends CardImpl {

    public DereviEmpyrialTactician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever Derevi, Empyrial Tactician enters the battlefield or a creature you control deals combat damage to a player, you may tap or untap target permanent.
        Ability ability = new DereviEmpyrialTacticianTriggeredAbility(new MayTapOrUntapTargetEffect());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);

        // {1}{G}{W}{U}: Put Derevi onto the battlefield from the command zone.
        this.addAbility(new DereviEmpyrialTacticianAbility());
    }

    private DereviEmpyrialTactician(final DereviEmpyrialTactician card) {
        super(card);
    }

    @Override
    public DereviEmpyrialTactician copy() {
        return new DereviEmpyrialTactician(this);
    }
}

class DereviEmpyrialTacticianTriggeredAbility extends TriggeredAbilityImpl {

    public DereviEmpyrialTacticianTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public DereviEmpyrialTacticianTriggeredAbility(DereviEmpyrialTacticianTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD 
                || event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                && event.getTargetId().equals(getSourceId())) {
            return true;
        }
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            if (((DamagedPlayerEvent) event).isCombatDamage()) {
                Permanent creature = game.getPermanent(event.getSourceId());
                if (creature != null 
                        && creature.isControlledBy(controllerId)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} enters the battlefield or a creature you control deals combat damage to a player, you may tap or untap target permanent.";
    }

    @Override
    public DereviEmpyrialTacticianTriggeredAbility copy() {
        return new DereviEmpyrialTacticianTriggeredAbility(this);
    }
}

class DereviEmpyrialTacticianAbility extends ActivatedAbilityImpl {

    public DereviEmpyrialTacticianAbility() {
        super(Zone.COMMAND, new PutCommanderOnBattlefieldEffect(), new ManaCostsImpl<>("{1}{G}{W}{U}"));
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        Zone currentZone = game.getState().getZone(this.getSourceId());
        if (currentZone != Zone.COMMAND) {
            return ActivationStatus.getFalse();
        }
        return super.canActivate(playerId, game);
    }

    public DereviEmpyrialTacticianAbility(DereviEmpyrialTacticianAbility ability) {
        super(ability);
    }

    @Override
    public DereviEmpyrialTacticianAbility copy() {
        return new DereviEmpyrialTacticianAbility(this);
    }

}

class PutCommanderOnBattlefieldEffect extends OneShotEffect {

    public PutCommanderOnBattlefieldEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put Derevi onto the battlefield from the command zone";
    }

    public PutCommanderOnBattlefieldEffect(final PutCommanderOnBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public PutCommanderOnBattlefieldEffect copy() {
        return new PutCommanderOnBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
            return true;
        }
        return false;
    }
}
