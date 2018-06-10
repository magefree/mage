
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WolfToken;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author nantuko
 */
public final class GarrukRelentless extends CardImpl {

    public GarrukRelentless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{3}{G}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GARRUK);

        this.transformable = true;
        this.secondSideCardClazz = GarrukTheVeilCursed.class;

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // When Garruk Relentless has two or fewer loyalty counters on him, transform him.
        this.addAbility(new TransformAbility());
        this.addAbility(new GarrukRelentlessTriggeredAbility());

        // 0: Garruk Relentless deals 3 damage to target creature. That creature deals damage equal to its power to him
        LoyaltyAbility ability1 = new LoyaltyAbility(new GarrukRelentlessDamageEffect(), 0);
        ability1.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability1);

        // 0: Create a 2/2 green Wolf creature token.
        LoyaltyAbility ability2 = new LoyaltyAbility(new CreateTokenEffect(new WolfToken()), 0);
        this.addAbility(ability2);
    }

    public GarrukRelentless(final GarrukRelentless card) {
        super(card);
    }

    @Override
    public GarrukRelentless copy() {
        return new GarrukRelentless(this);
    }
}

class GarrukRelentlessTriggeredAbility extends TriggeredAbilityImpl {

    public GarrukRelentlessTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TransformSourceEffect(true), false);
    }

    public GarrukRelentlessTriggeredAbility(GarrukRelentlessTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GarrukRelentlessTriggeredAbility copy() {
        return new GarrukRelentlessTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLANESWALKER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(sourceId)) {
            Permanent permanent = game.getPermanent(sourceId);
            if (permanent != null && !permanent.isTransformed() && permanent.getCounters(game).getCount(CounterType.LOYALTY) <= 2) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When Garruk Relentless has two or fewer loyalty counters on him, transform him.";
    }
}

class GarrukRelentlessDamageEffect extends OneShotEffect {

    public GarrukRelentlessDamageEffect() {
        super(Outcome.Damage);
        staticText = "Garruk Relentless deals 3 damage to target creature. That creature deals damage equal to its power to him";
    }

    public GarrukRelentlessDamageEffect(GarrukRelentlessDamageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null) {
            int damage = permanent.getPower().getValue();
            permanent.damage(3, source.getSourceId(), game, false, true);
            if (damage > 0) {
                Permanent garruk = game.getPermanent(source.getSourceId());
                if (garruk != null) {
                    garruk.damage(damage, permanent.getId(), game, false, true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public GarrukRelentlessDamageEffect copy() {
        return new GarrukRelentlessDamageEffect(this);
    }

}
