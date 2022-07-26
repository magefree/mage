package mage.cards.l;

import mage.MageInt;
import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.Iterator;
import java.util.UUID;

/**
 * @author awjackson
 */
public final class LiegeOfTheTangle extends CardImpl {

    public LiegeOfTheTangle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Liege of the Tangle deals combat damage to a player, you may choose any number of target lands you control
        // and put an awakening counter on each of them. Each of those lands is an 8/8 green Elemental creature
        // for as long as it has an awakening counter on it. They're still lands.
        Effect effect = new AddCountersTargetEffect(CounterType.AWAKENING.createInstance());
        effect.setText("you may choose any number of target lands you control and put an awakening counter on each of them");
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(effect, false);
        ability.addEffect(new LiegeOfTheTangleEffect());
        ability.addTarget(new TargetPermanent(0, Integer.MAX_VALUE, StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS));
        this.addAbility(ability);
    }

    private LiegeOfTheTangle(final LiegeOfTheTangle card) {
        super(card);
    }

    @Override
    public LiegeOfTheTangle copy() {
        return new LiegeOfTheTangle(this);
    }
}

class LiegeOfTheTangleEffect extends ContinuousEffectImpl {

    public LiegeOfTheTangleEffect() {
        super(Duration.EndOfGame, Outcome.BecomeCreature);
        staticText = "each of those lands is an 8/8 green Elemental creature for as long as it has an awakening counter on it. They're still lands";
    }

    public LiegeOfTheTangleEffect(final LiegeOfTheTangleEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext(); ) {
            Permanent perm = it.next().getPermanent(game);
            if (perm == null) {
                it.remove();
                continue;
            }
            if (perm.getCounters(game).getCount(CounterType.AWAKENING) < 1) {
                continue;
            }
            switch (layer) {
                case TypeChangingEffects_4:
                    perm.addCardType(game, CardType.CREATURE);
                    perm.addSubType(game, SubType.ELEMENTAL);
                    break;
                case ColorChangingEffects_5:
                    perm.getColor(game).addColor(ObjectColor.GREEN);
                    break;
                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        perm.getPower().setValue(8);
                        perm.getToughness().setValue(8);
                    }
                    break;
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
            for (UUID permId : targetPointer.getTargets(game, source)) {
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
        return layer == Layer.PTChangingEffects_7
                || layer == Layer.ColorChangingEffects_5
                || layer == Layer.TypeChangingEffects_4;
    }
}
