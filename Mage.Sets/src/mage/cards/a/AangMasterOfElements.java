package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.game.Game;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AangMasterOfElements extends CardImpl {

    private static final FilterCard filter = new FilterCard("spells");

    public AangMasterOfElements(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Spells you cast cost {W}{U}{B}{R}{G} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(
                filter, new ManaCostsImpl<>("{W}{U}{B}{R}{G}"), StaticValue.get(1), true
        )));

        // At the beginning of each upkeep, you may transform Aang, Master of Elements. If you do, you gain 4 life, draw four cards, put four +1/+1 counters on him, and he deals 4 damage to each opponent.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                TargetController.ANY,
                new DoIfCostPaid(new GainLifeEffect(4), new AangMasterOfElementsCost())
                        .addEffect(new DrawCardSourceControllerEffect(4).concatBy(","))
                        .addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(4))
                                .setText(", put four +1/+1 counters on him"))
                        .addEffect(new DamagePlayersEffect(4, TargetController.OPPONENT)
                                .setText(", and he deals 4 damage to each opponent")),
                false
        ));
    }

    private AangMasterOfElements(final AangMasterOfElements card) {
        super(card);
    }

    @Override
    public AangMasterOfElements copy() {
        return new AangMasterOfElements(this);
    }
}

class AangMasterOfElementsCost extends CostImpl {

    AangMasterOfElementsCost() {
        super();
        text = "transform {this}";
    }

    private AangMasterOfElementsCost(final AangMasterOfElementsCost cost) {
        super(cost);
    }

    @Override
    public AangMasterOfElementsCost copy() {
        return new AangMasterOfElementsCost(this);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .filter(Card::isTransformable)
                .isPresent();
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        paid = Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .filter(permanent -> permanent.transform(source, game))
                .isPresent();
        return paid;
    }
}
