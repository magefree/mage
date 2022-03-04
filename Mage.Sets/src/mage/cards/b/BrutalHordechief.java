package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleAllEffect;
import mage.abilities.effects.common.combat.ChooseBlockersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.ControlCombatRedundancyWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BrutalHordechief extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public BrutalHordechief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.ORC, SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a creature you control attacks, defending player loses 1 life and you gain 1 life.
        this.addAbility(new BrutalHordechiefTriggeredAbility());

        // {3}{R/W}{R/W}: Creatures your opponents control block this turn if able, and you choose how those creatures block.
        Ability ability = new SimpleActivatedAbility(
                new BlocksIfAbleAllEffect(filter, Duration.EndOfTurn), new ManaCostsImpl<>("{3}{R/W}{R/W}")
        );
        ability.addEffect(new ChooseBlockersEffect(Duration.EndOfTurn).setText("and you choose how those creatures block"));
        ability.addWatcher(new ControlCombatRedundancyWatcher());
        this.addAbility(ability);
    }

    private BrutalHordechief(final BrutalHordechief card) {
        super(card);
    }

    @Override
    public BrutalHordechief copy() {
        return new BrutalHordechief(this);
    }
}

class BrutalHordechiefTriggeredAbility extends TriggeredAbilityImpl {

    public BrutalHordechiefTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1));
        this.addEffect(new GainLifeEffect(1));
    }

    public BrutalHordechiefTriggeredAbility(final BrutalHordechiefTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BrutalHordechiefTriggeredAbility copy() {
        return new BrutalHordechiefTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent source = game.getPermanent(event.getSourceId());
        if (source != null && source.isControlledBy(getControllerId())) {
            UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(event.getSourceId(), game);
            this.getEffects().setTargetPointer(new FixedTarget(defendingPlayerId));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control attacks, defending player loses 1 life and you gain 1 life.";
    }
}
