package mage.cards.q;

import mage.MageIdentifier;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAnyTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class QuilledGreatwurm extends CardImpl {

    public QuilledGreatwurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a creature you control deals combat damage during your turn, put that many +1/+1 counters on it.
        this.addAbility(new DealsDamageToAnyTriggeredAbility(
                Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance(), SavedDamageValue.MANY),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE, SetTargetPointer.PERMANENT, true, false
        ).withTriggerCondition(MyTurnCondition.instance));

        // You may cast this card from your graveyard by removing six counters from among creatures you control in addition to paying its other costs.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new QuilledGreatwurmEffect()).setIdentifier(MageIdentifier.QuilledGreatwurmAlternateCast));
    }

    private QuilledGreatwurm(final QuilledGreatwurm card) {
        super(card);
    }

    @Override
    public QuilledGreatwurm copy() {
        return new QuilledGreatwurm(this);
    }
}

class QuilledGreatwurmEffect extends AsThoughEffectImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    QuilledGreatwurmEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        this.staticText = "you may cast this card from your graveyard by removing six counters " +
                "from among creatures you control in addition to paying its other costs";
    }

    private QuilledGreatwurmEffect(final QuilledGreatwurmEffect effect) {
        super(effect);
    }

    @Override
    public QuilledGreatwurmEffect copy() {
        return new QuilledGreatwurmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectControllerId, Game game) {
        if (!source.getSourceId().equals(objectId)
                || !source.isControlledBy(affectControllerId)
                || game.getState().getZone(objectId) != Zone.GRAVEYARD) {
            return false;
        }
        Player controller = game.getPlayer(affectControllerId);
        if (controller == null) {
            return false;
        }
        Costs<Cost> costs = new CostsImpl<>();
        costs.add(new RemoveCounterCost(new TargetControlledPermanent(1, 6, filter, true), null, 6));
        controller.setCastSourceIdWithAlternateMana(
                objectId, new ManaCostsImpl<>("{4}{G}{G}"), costs,
                MageIdentifier.QuilledGreatwurmAlternateCast
        );
        return true;
    }
}
