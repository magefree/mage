package mage.cards.x;

import mage.MageInt;
import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetLandPermanent;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author Grath
 */
public final class XolatoyacTheSmilingFlood extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("permanent with a counter on it");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public XolatoyacTheSmilingFlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SALAMANDER);
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever Xolatoyac, the Smiling Flood enters the battlefield or attacks, put a flood counter on target land.
        // That land is an Island in addition to its other types for as long as it has a flood counter on it.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new AddCountersTargetEffect(CounterType.FLOOD.createInstance()));
        ability.addEffect(new XolatoyacTheSmilingFloodEffect());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);

        // At the beginning of your end step, untap each permanent you control with a counter on it.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new UntapAllEffect(filter)
                        .setText("untap each permanent you control with a counter on it")
        ));
    }

    private XolatoyacTheSmilingFlood(final XolatoyacTheSmilingFlood card) {
        super(card);
    }

    @Override
    public XolatoyacTheSmilingFlood copy() {
        return new XolatoyacTheSmilingFlood(this);
    }
}

class XolatoyacTheSmilingFloodEffect extends BecomesBasicLandTargetEffect {

    XolatoyacTheSmilingFloodEffect() {
        super(Duration.Custom, false, false, SubType.ISLAND);
        staticText = "That land is an Island in addition to its other types for as long as it has a flood counter on it";
    }

    private XolatoyacTheSmilingFloodEffect(final XolatoyacTheSmilingFloodEffect effect) {
        super(effect);
    }

    @Override
    public XolatoyacTheSmilingFloodEffect copy() {
        return new XolatoyacTheSmilingFloodEffect(this);
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Permanent land = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (land == null || land.getCounters(game).getCount(CounterType.FLOOD) < 1) {
            discard();
            return false;
        }
        affectedObjects.add(land);
        return true;
    }
}
