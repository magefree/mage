
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantAttackBlockUnlessPaysSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author emerald000 & L_J
 */
public final class MyrPrototype extends CardImpl {

    public MyrPrototype(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.MYR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, put a +1/+1 counter on Myr Prototype.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), TargetController.YOU, false));

        // Myr Prototype can't attack or block unless you pay {1} for each +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MyrPrototypeCantAttackUnlessYouPayEffect()));
    }

    private MyrPrototype(final MyrPrototype card) {
        super(card);
    }

    @Override
    public MyrPrototype copy() {
        return new MyrPrototype(this);
    }
}

class MyrPrototypeCantAttackUnlessYouPayEffect extends CantAttackBlockUnlessPaysSourceEffect {

    MyrPrototypeCantAttackUnlessYouPayEffect() {
        super(new ManaCostsImpl<>("{0}"), RestrictType.ATTACK_AND_BLOCK);
        staticText = "{this} can't attack or block unless you pay {1} for each +1/+1 counter on it";
    }

    MyrPrototypeCantAttackUnlessYouPayEffect(MyrPrototypeCantAttackUnlessYouPayEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getSourceId().equals(event.getSourceId());
    }

    @Override
    public ManaCosts getManaCostToPay(GameEvent event, Ability source, Game game) {
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (sourceObject != null) {
            int counter = sourceObject.getCounters(game).getCount(CounterType.P1P1);
            if (counter > 0) {
                return new ManaCostsImpl<>("{" + counter + '}');
            }
        }
        return null;
    }

    @Override
    public MyrPrototypeCantAttackUnlessYouPayEffect copy() {
        return new MyrPrototypeCantAttackUnlessYouPayEffect(this);
    }

}
