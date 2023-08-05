package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class AnakinSkywalker extends CardImpl {

    public AnakinSkywalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SITH);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.secondSideCardClazz = mage.cards.d.DarthVader.class;

        // Whenever another creature dies, put a +1/+1 counter on Anakin Skywalker.
        this.addAbility(new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, true));

        // Sacrifice another creature: Target creature gets -1/-1 until end of turn. Activate this ability only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(-1, -1, Duration.EndOfTurn),
                new SacrificeTargetCost(new TargetControlledCreaturePermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // If Anakin Skywalker would be destroyed, regenerate, then transform him instead.
        this.addAbility(new TransformAbility());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AnakinSkywalkerEffect()));

    }

    private AnakinSkywalker(final AnakinSkywalker card) {
        super(card);
    }

    @Override
    public AnakinSkywalker copy() {
        return new AnakinSkywalker(this);
    }
}

class AnakinSkywalkerEffect extends ReplacementEffectImpl {

    AnakinSkywalkerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Transform);
        staticText = "If {this} would die, regenerate and transform him instead";
    }

    AnakinSkywalkerEffect(final AnakinSkywalkerEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            permanent.regenerate(source, game);
            return new TransformSourceEffect().apply(game, source);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId()) && ((ZoneChangeEvent) event).isDiesEvent();
    }

    @Override
    public AnakinSkywalkerEffect copy() {
        return new AnakinSkywalkerEffect(this);
    }
}
