
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.TargetPermanent;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author LevelX2
 */
public final class ShieldDancer extends CardImpl {

    public ShieldDancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {2}{W}: The next time target attacking creature would deal combat damage to Shield Dancer this turn, that creature deals that damage to itself instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShieldDancerRedirectionEffect(), new ManaCostsImpl<>("{2}{W}"));
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private ShieldDancer(final ShieldDancer card) {
        super(card);
    }

    @Override
    public ShieldDancer copy() {
        return new ShieldDancer(this);
    }
}

class ShieldDancerRedirectionEffect extends RedirectionEffect {

    public ShieldDancerRedirectionEffect() {
        super(Duration.EndOfTurn);
        staticText = "The next time target attacking creature would deal combat damage to {this} this turn, that creature deals that damage to itself instead";
    }

    public ShieldDancerRedirectionEffect(final ShieldDancerRedirectionEffect effect) {
        super(effect);
    }

    @Override
    public ShieldDancerRedirectionEffect copy() {
        return new ShieldDancerRedirectionEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGE_PERMANENT;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())
                && event.getSourceId().equals(source.getTargets().get(0).getFirstTarget())) {
            DamageEvent damageEvent = (DamageEvent) event;
            if (damageEvent.isCombatDamage()) {
                TargetPermanent target = new TargetPermanent();
                target.add(source.getTargets().get(0).getFirstTarget(), game);
                redirectTarget = target;
            }
            return true;
        }
        return false;
    }

}
