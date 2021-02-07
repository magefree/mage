package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedThisTurnWatcher;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KardurDoomscourge extends CardImpl {

    public KardurDoomscourge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Kardur, Doomscourge enters the battlefield, until your next turn, creatures your opponents control attack each combat if able and attack a player other than you if able.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AttacksIfAbleAllEffect(
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE, Duration.UntilYourNextTurn
        ).setText("until your next turn, creatures your opponents control attack each combat if able"));
        ability.addEffect(new KardurDoomscourgeEffect());
        ability.addWatcher(new AttackedThisTurnWatcher());
        this.addAbility(ability);

        // Whenever an attacking creature dies, each opponent loses 1 life and you gain 1 life.
        this.addAbility(new KardurDoomscourgeTriggeredAbility());
    }

    private KardurDoomscourge(final KardurDoomscourge card) {
        super(card);
    }

    @Override
    public KardurDoomscourge copy() {
        return new KardurDoomscourge(this);
    }
}

class KardurDoomscourgeEffect extends RestrictionEffect {

    KardurDoomscourgeEffect() {
        super(Duration.UntilYourNextTurn);
        staticText = "and attack a player other than you if able";
    }

    private KardurDoomscourgeEffect(final KardurDoomscourgeEffect effect) {
        super(effect);
    }

    @Override
    public KardurDoomscourgeEffect copy() {
        return new KardurDoomscourgeEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return game.getOpponents(permanent.getControllerId()).contains(source.getControllerId());
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (defenderId == null
                || game.getState().getPlayersInRange(attacker.getControllerId(), game).size() == 2) {  // just 2 players left, so it may attack you
            return true;
        }
        // A planeswalker controlled by the controller is the defender
        if (game.getPermanent(defenderId) != null) {
            return !game.getPermanent(defenderId).getControllerId().equals(source.getControllerId());
        }
        // The controller is the defender
        return !defenderId.equals(source.getControllerId());
    }
}

class KardurDoomscourgeTriggeredAbility extends TriggeredAbilityImpl {

    public KardurDoomscourgeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeOpponentsEffect(1), false);
        this.addEffect(new GainLifeEffect(1).concatBy("and"));
    }

    private KardurDoomscourgeTriggeredAbility(final KardurDoomscourgeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KardurDoomscourgeTriggeredAbility copy() {
        return new KardurDoomscourgeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DECLARE_ATTACKERS_STEP:
            case END_COMBAT_STEP_POST:
            case ZONE_CHANGE:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (event.getType()) {
            case DECLARE_ATTACKERS_STEP:
                game.getState().setValue(this.getId() + "Attackers", game.getCombat().getAttackers());
                return false;
            case END_COMBAT_STEP_POST:
                game.getState().setValue(this.getId() + "Attackers", null);
                return false;
            case ZONE_CHANGE:
                ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
                List<UUID> attackers = (List<UUID>) game.getState().getValue(this.getId() + "Attackers");
                return zEvent.isDiesEvent() && attackers != null && attackers.contains(zEvent.getTargetId());
            default:
                return false;
        }
    }

    @Override
    public String getRule() {
        return "Whenever an attacking creature dies, " + super.getRule();
    }
}
