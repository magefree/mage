package mage.cards.m;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MagusOfTheOrder extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("green creature card");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public MagusOfTheOrder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {G}, {T}, Sacrifice Magus of the Order and another green creature: Search your library for a green creature card and put it onto the battlefield. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(1, filter), false, true
        ), new ManaCostsImpl("{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new MagusOfTheOrderCost());
        this.addAbility(ability);
    }

    private MagusOfTheOrder(final MagusOfTheOrder card) {
        super(card);
    }

    @Override
    public MagusOfTheOrder copy() {
        return new MagusOfTheOrder(this);
    }
}

class MagusOfTheOrderCost extends CostImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("another green creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    private final Cost cost1;
    private final Cost cost2;

    MagusOfTheOrderCost() {
        super();
        this.cost1 = new SacrificeSourceCost();
        this.cost2 = new SacrificeTargetCost(new TargetControlledPermanent(filter));
        this.text = "sacrifice {this} and another green creature";
    }

    private MagusOfTheOrderCost(final MagusOfTheOrderCost cost) {
        super(cost);
        this.cost1 = cost.cost1.copy();
        this.cost2 = cost.cost2.copy();
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return cost1.canPay(ability, sourceId, controllerId, game)
                && cost2.canPay(ability, sourceId, controllerId, game);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        return cost1.pay(ability, game, sourceId, controllerId, noMana, costToPay)
                && cost2.pay(ability, game, sourceId, controllerId, noMana, costToPay);
    }

    @Override
    public boolean isPaid() {
        return cost1.isPaid() && cost2.isPaid();
    }

    @Override
    public void clearPaid() {
        cost1.clearPaid();
        cost2.clearPaid();
    }

    @Override
    public void setPaid() {
        cost1.setPaid();
        cost2.setPaid();
    }

    @Override
    public MagusOfTheOrderCost copy() {
        return new MagusOfTheOrderCost(this);
    }
}