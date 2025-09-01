package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
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
        Ability ability = new LoyaltyAbility(new DamageTargetEffect(3), 0);
        ability.addEffect(new GarrukRelentlessDamageEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // 0: Create a 2/2 green Wolf creature token.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new WolfToken()), 0));
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

    private GarrukRelentlessStateTrigger(final GarrukRelentlessStateTrigger ability) {
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

    GarrukRelentlessDamageEffect() {
        super(Outcome.Damage);
        staticText = "That creature deals damage equal to its power to him";
    }

    private GarrukRelentlessDamageEffect(final GarrukRelentlessDamageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Permanent creature = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        return permanent != null
                && creature != null
                && permanent.damage(creature.getPower().getValue(), creature.getId(), source, game) > 0;
    }

    @Override
    public GarrukRelentlessDamageEffect copy() {
        return new GarrukRelentlessDamageEffect(this);
    }

}
