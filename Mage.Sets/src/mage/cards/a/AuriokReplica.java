package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.PreventDamageEvent;
import mage.game.events.PreventedDamageEvent;
import mage.target.TargetSource;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class AuriokReplica extends CardImpl {

    public AuriokReplica(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {W}, Sacrifice Auriok Replica: Prevent all damage a source of your choice would deal to you this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AuriokReplicaEffect(), new ManaCostsImpl<>("{W}"));
        ability.addTarget(new TargetSource());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private AuriokReplica(final AuriokReplica card) {
        super(card);
    }

    @Override
    public AuriokReplica copy() {
        return new AuriokReplica(this);
    }

}

class AuriokReplicaEffect extends PreventionEffectImpl {

    public AuriokReplicaEffect() {
        super(Duration.EndOfTurn);
        staticText = "Prevent all damage a source of your choice would deal to you this turn";
    }

    public AuriokReplicaEffect(final AuriokReplicaEffect effect) {
        super(effect);
    }

    @Override
    public AuriokReplicaEffect copy() {
        return new AuriokReplicaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new PreventDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), event.getAmount(), ((DamageEvent) event).isCombatDamage());
        if (!game.replaceEvent(preventEvent)) {
            int damage = event.getAmount();
            event.setAmount(0);
            game.fireEvent(new PreventedDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), damage));
        }
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            return event.getTargetId().equals(source.getControllerId()) && event.getSourceId().equals(this.getTargetPointer().getFirst(game, source));
        }
        return false;
    }

}