

package mage.cards.l;

import java.util.Iterator;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.custom.ElementalCreatureToken;
import mage.target.Target;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Loki
 */
public final class LiegeOfTheTangle extends CardImpl {

    public LiegeOfTheTangle (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(new LiegeOfTheTangleTriggeredAbility());
    }

    public LiegeOfTheTangle (final LiegeOfTheTangle card) {
        super(card);
    }

    @Override
    public LiegeOfTheTangle copy() {
        return new LiegeOfTheTangle(this);
    }
}

class LiegeOfTheTangleTriggeredAbility extends TriggeredAbilityImpl {
    LiegeOfTheTangleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.AWAKENING.createInstance()));
        this.addEffect(new LiegeOfTheTangleEffect());
        Target target = new TargetLandPermanent(0, Integer.MAX_VALUE, StaticFilters.FILTER_LANDS, true);
        this.addTarget(target);
    }

    public LiegeOfTheTangleTriggeredAbility(final LiegeOfTheTangleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LiegeOfTheTangleTriggeredAbility copy() {
        return new LiegeOfTheTangleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
        Permanent p = game.getPermanent(event.getSourceId());
        return damageEvent.isCombatDamage() && p != null && p.getId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, you may choose any number of target lands you control and put an awakening counter on each of them. Each of those lands is an 8/8 green Elemental creature for as long as it has an awakening counter on it. They're still lands.";
    }
}

class LiegeOfTheTangleEffect extends ContinuousEffectImpl {

    private static ElementalCreatureToken token = new ElementalCreatureToken(8, 8, "8/8 green Elemental creature", new ObjectColor("G"));

    public LiegeOfTheTangleEffect() {
        super(Duration.EndOfGame, Outcome.BecomeCreature);
    }

    public LiegeOfTheTangleEffect(final LiegeOfTheTangleEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) { 
            Permanent perm = it.next().getPermanent(game);
            if (perm != null) {
                if (perm.getCounters(game).getCount(CounterType.AWAKENING) > 0) {
                    switch (layer) {
                        case TypeChangingEffects_4:
                            if (sublayer == SubLayer.NA) {
                                perm.addCardTypes(token.getCardType());
                                perm.getSubtype(game).addAll(token.getSubtype(game));
                            }
                            break;
                        case ColorChangingEffects_5:
                            if (sublayer == SubLayer.NA) {
                                perm.getColor(game).setColor(token.getColor(game));
                            }
                            break;
                        case PTChangingEffects_7:
                            if (sublayer == SubLayer.SetPT_7b) {
                                perm.getPower().setValue(token.getPower().getValue());
                                perm.getToughness().setValue(token.getToughness().getValue());
                            }
                            break;
                    }
                }
            } else {
                it.remove();
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            for (UUID permId: targetPointer.getTargets(game, source)) {
                affectedObjectList.add(new MageObjectReference(permId, game));
            }
        }
    }

    @Override
    public LiegeOfTheTangleEffect copy() {
        return new LiegeOfTheTangleEffect(this);
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.ColorChangingEffects_5 || layer == layer.TypeChangingEffects_4;
    }

}


