
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author Styxo/spjspj
 */
public final class JangoFett extends CardImpl {

    public JangoFett(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HUNTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Jango Fett attacks, put a bounty counter on target creature an opponent controls.
        Ability ability = new AttacksTriggeredAbility(new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()), false);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // Whenever Jango Fett attacks, it deals X damage to defending player and target creature he or she controls, where X is the number of creatures defending player controls with a bounty counter on them.
        this.addAbility(new JangoFettTriggeredAbility(new JangoFettEffect(), false));
    }

    public JangoFett(final JangoFett card) {
        super(card);
    }

    @Override
    public JangoFett copy() {
        return new JangoFett(this);
    }
}

class JangoFettTriggeredAbility extends TriggeredAbilityImpl {

    protected String text;

    public JangoFettTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public JangoFettTriggeredAbility(Effect effect, boolean optional, String text) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.text = text;
    }

    public JangoFettTriggeredAbility(final JangoFettTriggeredAbility ability) {
        super(ability);
        this.text = ability.text;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            UUID defenderId = game.getCombat().getDefendingPlayerId(getSourceId(), game);
            if (defenderId != null) {
                this.getTargets().clear();
                FilterCreaturePermanent filter = new FilterCreaturePermanent("target creature defending player controls");
                filter.add(new ControllerIdPredicate(defenderId));
                TargetPermanent target = new TargetPermanent(filter);
                this.addTarget(target);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        if (text == null || text.isEmpty()) {
            return "Whenever {this} attacks, " + super.getRule();
        }
        return text;
    }

    @Override
    public JangoFettTriggeredAbility copy() {
        return new JangoFettTriggeredAbility(this);
    }
}

class JangoFettEffect extends OneShotEffect {

    public JangoFettEffect() {
        super(Outcome.Damage);
        this.staticText = "it deals X damage to defending player and target creature he or she controls, where X is the number of creatures defending player controls with a bounty counter on them";
    }

    public JangoFettEffect(final JangoFettEffect ability) {
        super(ability);
    }

    @Override
    public JangoFettEffect copy() {
        return new JangoFettEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getSourceId());
        if (creature == null) {
            return false;
        }

        // Count the number of creatures attacked opponent controls with a bounty counter
        UUID defenderId = game.getCombat().getDefendingPlayerId(creature.getId(), game);
        int count = 0;
        if (defenderId != null) {
            FilterCreaturePermanent bountyFilter = new FilterCreaturePermanent("creatures defending player controls with a bounty counter");
            bountyFilter.add(new CounterPredicate(CounterType.BOUNTY));
            count = game.getBattlefield().countAll(bountyFilter, defenderId, game);
        }

        if (count == 0) {
            return false;
        }

        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (targetCreature != null) {
            targetCreature.damage(count, source.getSourceId(), game, false, true);
        }
        Player defender = game.getPlayer(defenderId);
        defender.damage(count, source.getSourceId(), game, false, true);

        return true;
    }

}
