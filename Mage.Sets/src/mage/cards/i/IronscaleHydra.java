package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IronscaleHydra extends CardImpl {

    public IronscaleHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // If a creature would deal combat damage to Ironscale Hydra, prevent that damage and put a +1/+1 counter on Ironscale Hydra.
        this.addAbility(new SimpleStaticAbility(new IronscaleHydraEffect()));
    }

    private IronscaleHydra(final IronscaleHydra card) {
        super(card);
    }

    @Override
    public IronscaleHydra copy() {
        return new IronscaleHydra(this);
    }
}

class IronscaleHydraEffect extends PreventionEffectImpl {

    private static final Effect effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance());

    IronscaleHydraEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, true, false);
        staticText = "If a creature would deal combat damage to {this}, " +
                "prevent that damage and put a +1/+1 counter on {this}.";
    }

    private IronscaleHydraEffect(final IronscaleHydraEffect effect) {
        super(effect);
    }

    @Override
    public IronscaleHydraEffect copy() {
        return new IronscaleHydraEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!super.applies(event, source, game)
                || !event.getTargetId().equals(source.getSourceId())) {
            return false;
        }
        Permanent damageSource = game.getPermanent(event.getSourceId());
        return damageSource != null && damageSource.isCreature(game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        effect.apply(game, source);
        return super.replaceEvent(event, source, game);
    }
}
