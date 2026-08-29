package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author miesma
 */
public final class DesertWereWorm extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Mountain you control");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public DesertWereWorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        //This creature gets +2/+0 for each Mountain you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(new PermanentsOnBattlefieldCount(filter, 2),
                new PermanentsOnBattlefieldCount(filter, 0),
                Duration.WhileOnBattlefield)));

        // Whenever you attack with creatures with total power 12 or greater for the first time each turn,
        // untap all attacking creatures. After this phase, there is an additional combat phase.
        Ability ability = new DesertWereWormAttackAbility(new UntapAllControllerEffect(
                StaticFilters.FILTER_ATTACKING_CREATURES,
                "untap all attacking creatures"
        ));
        ability.addEffect(new AdditionalCombatPhaseEffect());
        this.addAbility(ability);
    }

    private DesertWereWorm(final DesertWereWorm card) {
        super(card);
    }

    @Override
    public DesertWereWorm copy() {
        return new DesertWereWorm(this);
    }

}

class DesertWereWormAttackAbility extends TriggeredAbilityImpl {

    public DesertWereWormAttackAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        this.withRuleTextReplacement(false);
        setTriggerPhrase("Whenever you attack with creatures with total power 12 or greater for the first time each turn, ");
    }

    private DesertWereWormAttackAbility(final DesertWereWormAttackAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(game.getCombat().getAttackingPlayerId())) {
            return false;
        }
        // First attack with power 12 or greater not overall first attack
        // Could be triggered in an additional combat
        // From any creatures you control attacking
        if (TriggeredAbility.checkDidThisTurn(this,game)) {
            return false;
        }
        List<Permanent> attackers = game
                .getCombat()
                .getAttackers()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(permanent -> StaticFilters.FILTER_PERMANENT_CREATURES.match(permanent, controllerId, this, game))
                .collect(Collectors.toList());
        if (!attackers.isEmpty()) {
            int power = 0;
            for (Permanent attacker : attackers) {
                if (attacker != null) {
                    power += attacker.getPower().getValue();
                }
            }
            if (power >= 12) {
                TriggeredAbility.setDidThisTurn(this, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public DesertWereWormAttackAbility copy() {
        return new DesertWereWormAttackAbility(this);
    }
}
