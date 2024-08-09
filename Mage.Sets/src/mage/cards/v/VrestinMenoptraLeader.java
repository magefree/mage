package mage.cards.v;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.AlienInsectToken;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Sidorovich77
 */
public final class VrestinMenoptraLeader extends CardImpl {
    
    public VrestinMenoptraLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vrestin enters with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())
        ));

        // When Vrestin enters, create X 1/1 green and white Alien Insect creature tokens with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new AlienInsectToken(), GetXValue.instance)
                        .setText("create X 1/1 green and white Alien Insect creature tokens with flying.")
        ));

        // Whenever you attack with one or more Insects, put a +1/+1 counter on each of them.
        this.addAbility(new VrestinMenoptraLeaderTriggeredAbility());
    }

    private VrestinMenoptraLeader(final VrestinMenoptraLeader card) {
        super(card);
    }

    @Override
    public VrestinMenoptraLeader copy() {
        return new VrestinMenoptraLeader(this);
    }
    class VrestinMenoptraLeaderTriggeredAbility extends TriggeredAbilityImpl {

        VrestinMenoptraLeaderTriggeredAbility() {
            super(Zone.BATTLEFIELD, null);
        }

        private VrestinMenoptraLeaderTriggeredAbility(final VrestinMenoptraLeaderTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public VrestinMenoptraLeaderTriggeredAbility copy() {
            return new VrestinMenoptraLeaderTriggeredAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            if (game.getCombat().getAttackingPlayerId().equals(getControllerId())) {
                int attackerCount = 0;
                Set<MageObjectReference> attackers = new HashSet();
                for (UUID attackerId : game.getCombat().getAttackers()) {
                    Permanent permanent = game.getPermanent(attackerId);
                    if (permanent.isCreature(game) && permanent.hasSubtype(SubType.INSECT, game))
                    {
                        attackerCount++;
                        attackers.add(new MageObjectReference(permanent, game));
                    }
                }
                if (attackerCount >= 1) {
                    this.getEffects().clear();
                    this.addEffect(new VrestinMenoptraLeaderEffect(attackers));
                    return true;
                }
            }
            return false;
        }

        @Override
        public String getRule() {
            return "Whenever you attack with one or more Insects, put a +1/+1 counter on each of them.";
        }
    }

    class VrestinMenoptraLeaderEffect extends OneShotEffect {

        private final Set<MageObjectReference> attackers;

        VrestinMenoptraLeaderEffect(Set<MageObjectReference> attackers) {
            super(Outcome.Benefit);
            this.attackers = attackers;
        }

        private VrestinMenoptraLeaderEffect(final VrestinMenoptraLeaderEffect effect) {
            super(effect);
            this.attackers = effect.attackers;
        }

        @Override
        public VrestinMenoptraLeaderEffect copy() {
            return new VrestinMenoptraLeaderEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            for (MageObjectReference mor : attackers) {
                Permanent permanent = mor.getPermanent(game);
                if (permanent != null) {
                    permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
                }
            }
            return true;
        }
    }
    
}
