package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.PreventDamageEvent;
import mage.game.events.PreventedDamageEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class UnbreathingHorde extends CardImpl {

    public UnbreathingHorde(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Unbreathing Horde enters the battlefield with a +1/+1 counter on it for each other Zombie you control and each Zombie card in your graveyard.
        this.addAbility(new EntersBattlefieldAbility(new UnbreathingHordeEffect1(), "with a +1/+1 counter on it for each other Zombie you control and each Zombie card in your graveyard"));

        // If Unbreathing Horde would be dealt damage, prevent that damage and remove a +1/+1 counter from it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new UnbreathingHordeEffect2()));
    }

    private UnbreathingHorde(final UnbreathingHorde card) {
        super(card);
    }

    @Override
    public UnbreathingHorde copy() {
        return new UnbreathingHorde(this);
    }
}

class UnbreathingHordeEffect1 extends OneShotEffect {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent();
    private static final FilterCreatureCard filter2 = new FilterCreatureCard();

    static {
        filter1.add(SubType.ZOMBIE.getPredicate());
        filter2.add(SubType.ZOMBIE.getPredicate());
    }

    public UnbreathingHordeEffect1() {
        super(Outcome.BoostCreature);
        staticText = "{this} enters the battlefield with a +1/+1 counter on it for each other Zombie you control and each Zombie card in your graveyard";
    }

    public UnbreathingHordeEffect1(final UnbreathingHordeEffect1 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent != null && player != null) {
            int amount = game.getBattlefield().countAll(filter1, source.getControllerId(), game);
            amount += player.getGraveyard().count(filter2, game);
            if (amount > 0) {
                permanent.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public UnbreathingHordeEffect1 copy() {
        return new UnbreathingHordeEffect1(this);
    }

}

class UnbreathingHordeEffect2 extends PreventionEffectImpl {

    public UnbreathingHordeEffect2() {
        super(Duration.WhileOnBattlefield);
        staticText = "If damage would be dealt to {this}, prevent that damage and remove a +1/+1 counter from it";
    }

    public UnbreathingHordeEffect2(final UnbreathingHordeEffect2 effect) {
        super(effect);
    }

    @Override
    public UnbreathingHordeEffect2 copy() {
        return new UnbreathingHordeEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        boolean retValue = false;
        GameEvent preventEvent = new PreventDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), event.getAmount(), ((DamageEvent) event).isCombatDamage());
        int damage = event.getAmount();
        if (!game.replaceEvent(preventEvent)) {
            event.setAmount(0);
            game.fireEvent(new PreventedDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), damage));
            retValue = true;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.removeCounters(CounterType.P1P1.createInstance(), source, game);
        }
        return retValue;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            return event.getTargetId().equals(source.getSourceId());
        }
        return false;
    }

}
