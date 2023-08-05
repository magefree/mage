
package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WolfToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class GarrukRelentless extends CardImpl {

    public GarrukRelentless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GARRUK);

        this.secondSideCardClazz = mage.cards.g.GarrukTheVeilCursed.class;

        this.setStartingLoyalty(3);

        // When Garruk Relentless has two or fewer loyalty counters on him, transform him.
        this.addAbility(new TransformAbility());
        this.addAbility(new GarrukRelentlessStateTrigger());

        // 0: Garruk Relentless deals 3 damage to target creature. That creature deals damage equal to its power to him
        LoyaltyAbility ability1 = new LoyaltyAbility(new GarrukRelentlessDamageEffect(), 0);
        ability1.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability1);

        // 0: Create a 2/2 green Wolf creature token.
        LoyaltyAbility ability2 = new LoyaltyAbility(new CreateTokenEffect(new WolfToken()), 0);
        this.addAbility(ability2);
    }

    private GarrukRelentless(final GarrukRelentless card) {
        super(card);
    }

    @Override
    public GarrukRelentless copy() {
        return new GarrukRelentless(this);
    }
}

class GarrukRelentlessStateTrigger extends StateTriggeredAbility {

    public GarrukRelentlessStateTrigger() {
        super(Zone.BATTLEFIELD, new TransformSourceEffect());
    }

    public GarrukRelentlessStateTrigger(final GarrukRelentlessStateTrigger ability) {
        super(ability);
    }

    @Override
    public GarrukRelentlessStateTrigger copy() {
        return new GarrukRelentlessStateTrigger(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        return permanent != null && permanent.getCounters(game).getCount(CounterType.LOYALTY) < 3;
    }

    @Override
    public String getRule() {
        return "When {this} has two or fewer loyalty counters on him, transform him.";
    }
}

class GarrukRelentlessDamageEffect extends OneShotEffect {

    public GarrukRelentlessDamageEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 3 damage to target creature. That creature deals damage equal to its power to him";
    }

    public GarrukRelentlessDamageEffect(GarrukRelentlessDamageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null) {
            int damage = permanent.getPower().getValue();
            permanent.damage(3, source.getSourceId(), source, game, false, true);
            if (damage > 0) {
                Permanent garruk = game.getPermanent(source.getSourceId());
                if (garruk != null) {
                    garruk.damage(damage, permanent.getId(), source, game, false, true);
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
