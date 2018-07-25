package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToACreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author NinthWorld
 */
public final class Disruptor extends CardImpl {

    public Disruptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{W}");
        
        this.subtype.add(SubType.PROTOSS);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Disruptor deals combat damage to a creature, it deals 1 damage to each other attacking or blocking creature controlled by the same player.
        this.addAbility(new DealsCombatDamageToACreatureTriggeredAbility(new DisruptorEffect(), false, true));
    }

    public Disruptor(final Disruptor card) {
        super(card);
    }

    @Override
    public Disruptor copy() {
        return new Disruptor(this);
    }
}

class DisruptorEffect extends OneShotEffect {

    public DisruptorEffect() {
        super(Outcome.Damage);
        staticText = "it deals 1 damage to each other attacking or blocking creature controlled by the same player";
    }

    public DisruptorEffect(final DisruptorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if(permanent != null) {
            FilterAttackingOrBlockingCreature filter = new FilterAttackingOrBlockingCreature();
            filter.add(new ControllerIdPredicate(permanent.getControllerId()));
            filter.add(Predicates.not(new PermanentIdPredicate(permanent.getId())));
            source.addEffect(new DamageAllEffect(1, filter));
            return true;
        }

        return false;
    }

    @Override
    public DisruptorEffect copy() {
        return new DisruptorEffect(this);
    }
}