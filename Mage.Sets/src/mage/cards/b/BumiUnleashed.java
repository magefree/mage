package mage.cards.b;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapAllLandsControllerEffect;
import mage.abilities.effects.common.combat.CantAttackAllEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.constants.*;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.turn.TurnMod;
import mage.target.common.TargetControlledLandPermanent;

/**
 *
 * @author Grath
 */
public final class BumiUnleashed extends CardImpl {

    public BumiUnleashed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Bumi enters, earthbend 4.
        Ability ability = new EntersBattlefieldTriggeredAbility(new EarthbendTargetEffect(4));
        ability.addTarget(new TargetControlledLandPermanent());
        this.addAbility(ability);

        // Whenever Bumi deals combat damage to a player, untap all lands you control. After this phase, there is an additional combat phase. Only land creatures can attack during that combat phase.
        ability = new DealsCombatDamageToAPlayerTriggeredAbility(new UntapAllLandsControllerEffect(), false);
        ability.addEffect(new BumiUnleashedEffect());
        this.addAbility(ability);
    }

    private BumiUnleashed(final BumiUnleashed card) {
        super(card);
    }

    @Override
    public BumiUnleashed copy() {
        return new BumiUnleashed(this);
    }
}

//Based on LastNightTogetherEffect
class BumiUnleashedEffect extends OneShotEffect {

    BumiUnleashedEffect() {
        super(Outcome.Benefit);
        staticText = "After this main phase, there is an additional combat phase. Only land creatures can attack during that combat phase";
    }

    private BumiUnleashedEffect(final BumiUnleashedEffect effect) {
        super(effect);
    }

    @Override
    public BumiUnleashedEffect copy() {
        return new BumiUnleashedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // At the start of that combat, add a restriction effect preventing other creatures from attacking.
        TurnMod combat = new TurnMod(game.getState().getActivePlayerId()).withExtraPhase(TurnPhase.COMBAT);
        game.getState().getTurnMods().add(combat);
        BumiUnleashedDelayedCantAttackAbility delayedTriggeredAbility = new BumiUnleashedDelayedCantAttackAbility(combat.getId());
        game.addDelayedTriggeredAbility(delayedTriggeredAbility, source);
        return true;
    }

}

class BumiUnleashedDelayedCantAttackAbility extends DelayedTriggeredAbility {

    private final UUID connectedTurnMod;

    BumiUnleashedDelayedCantAttackAbility(UUID connectedTurnMod) {
        super(null, Duration.EndOfTurn);
        FilterCreaturePermanent filterRestriction = new FilterCreaturePermanent();
        filterRestriction.add(Predicates.not(CardType.LAND.getPredicate()));
        this.addEffect(new CantAttackAllEffect(Duration.EndOfCombat, filterRestriction));
        this.usesStack = false; // don't show this to the user
        this.connectedTurnMod = connectedTurnMod;
    }

    BumiUnleashedDelayedCantAttackAbility(BumiUnleashedDelayedCantAttackAbility ability) {
        super(ability);
        this.connectedTurnMod = ability.connectedTurnMod;
    }

    @Override
    public BumiUnleashedDelayedCantAttackAbility copy() {
        return new BumiUnleashedDelayedCantAttackAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PHASE_CHANGED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return (event.getType() == GameEvent.EventType.PHASE_CHANGED && this.connectedTurnMod.equals(event.getSourceId()));
    }

    @Override
    public String getRule() {
        return "Only land creatures can attack during that combat phase";
    }
}
