package mage.cards.a;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Iterator;
import java.util.UUID;

/**
 * @author Styxo
 */
public final class AnakinSkywalker extends TransformingDoubleFacedCard {

    public AnakinSkywalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SITH},  "{3}{U}{B}{R}",
                "Darth Vader",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SITH}, "B");
        this.getLeftHalfCard().setPT(4, 4);
        this.getRightHalfCard().setPT(4, 4);

        // Whenever another creature dies, put a +1/+1 counter on Anakin Skywalker.
        this.getLeftHalfCard().addAbility(new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, true));

        // Sacrifice another creature: Target creature gets -1/-1 until end of turn. Activate this ability only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(-1, -1, Duration.EndOfTurn),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        ability.addTarget(new TargetCreaturePermanent());
        this.getLeftHalfCard().addAbility(ability);

        // If Anakin Skywalker would be destroyed, regenerate, then transform him instead.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new AnakinSkywalkerEffect()));

        // Darth Vader
        // Menace
        this.getRightHalfCard().addAbility(new MenaceAbility());

        // Lifelink
        this.getRightHalfCard().addAbility(LifelinkAbility.getInstance());

        // Whenever Darth Vader attacks, creatures defending player controls get -1/-1 until end of turn for each +1/+1 counter on Darth Vader.
        this.getRightHalfCard().addAbility(new AttacksTriggeredAbility(new UnboostCreaturesDefendingPlayerEffect(), false, null, SetTargetPointer.PLAYER));

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

    private AnakinSkywalkerEffect(final AnakinSkywalkerEffect effect) {
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


class UnboostCreaturesDefendingPlayerEffect extends ContinuousEffectImpl {

    UnboostCreaturesDefendingPlayerEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.UnboostCreature);
        staticText = "creatures defending player controls get -1/-1 until end of turn for each +1/+1 counter on Darth Vader";
    }

    private UnboostCreaturesDefendingPlayerEffect(final UnboostCreaturesDefendingPlayerEffect effect) {
        super(effect);
    }

    @Override
    public UnboostCreaturesDefendingPlayerEffect copy() {
        return new UnboostCreaturesDefendingPlayerEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (getAffectedObjectsSet()) {
            for (Permanent creature : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, getTargetPointer().getFirst(game, source), game)) {
                affectedObjectList.add(new MageObjectReference(creature, game));
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext(); ) {
            Permanent permanent = it.next().getPermanent(game);
            if (permanent != null) {
                int unboostCount = -1 * new CountersSourceCount(CounterType.P1P1).calculate(game, source, this);
                permanent.addPower(unboostCount);
                permanent.addToughness(unboostCount);
            } else {
                it.remove();
            }
        }
        return true;
    }
}

