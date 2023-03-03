package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.util.CardUtil;
import java.util.UUID;

/**
 * @author miesma
 */
public final class KinzuOfTheBleakCoven extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another nontoken creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public KinzuOfTheBleakCoven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever another nontoken creature you control dies, you may pay 2 Life and exile it. If you do, create a token that's a copy of that card, except it’s 1/1 and has toxic 1.
        Effect effect = new DoIfCostPaid(new mage.cards.k.KinzuOfTheBleakCovenEffect(), new KinzuOfTheBleakCovenCost(2), "Pay 2 Life and exile it?");
        this.addAbility(new DiesCreatureTriggeredAbility(effect,false, filter, true));

    }

    private KinzuOfTheBleakCoven(final mage.cards.k.KinzuOfTheBleakCoven card) {
        super(card);
    }

    @Override
    public mage.cards.k.KinzuOfTheBleakCoven copy() {
        return new mage.cards.k.KinzuOfTheBleakCoven(this);
    }
}

class KinzuOfTheBleakCovenEffect extends OneShotEffect {

    KinzuOfTheBleakCovenEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that’s a copy of that creature" +
                ", except it’s 1/1 and has toxic 1.";
    }

    private KinzuOfTheBleakCovenEffect(final mage.cards.k.KinzuOfTheBleakCovenEffect effect) {
        super(effect);
    }

    @Override
    public mage.cards.k.KinzuOfTheBleakCovenEffect copy() {
        return new mage.cards.k.KinzuOfTheBleakCovenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        return new CreateTokenCopyTargetEffect().setSavedPermanent(
                new PermanentCard(card, source.getControllerId(), game)
        ).setPermanentModifier((token, g) -> {
            token.setPower(1);                             // 1/1
            token.setToughness(1);
            token.addAbility(new ToxicAbility(1)); // Add Toxic (is additive)
        }).apply(game, source);
    }
}


class KinzuOfTheBleakCovenCost extends CostImpl {

    //New Cost as exiling the Card is part of the Cost.

    private DynamicValue amount;

    public KinzuOfTheBleakCovenCost(int amount) {
        super();
        this.amount = StaticValue.get(amount).copy();
        this.text = "2 Life and exile it";
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        int lifeToPayAmount = amount.calculate(game, ability, null);
        // Paying 0 life is not considered paying any life.
        if (lifeToPayAmount > 0 && !game.getPlayer(controllerId).canPayLifeCost(ability)) {
            return false;
        }
        Card card = game.getCard((ability.getAllEffects().get(0).getTargetPointer().getFirst(game, source)));
        if (card == null) {
            //Need to be able to Exile the Card as it is part of the cost. Can't pay if the target is not legal.
            return false;
        }
        System.out.println(text);
        return game.getPlayer(controllerId).getLife() >= lifeToPayAmount || lifeToPayAmount == 0;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }
        int lifeToPayAmount = amount.calculate(game, ability, null);
        this.paid = CardUtil.tryPayLife(lifeToPayAmount, controller, source, game);
        return this.paid;
    }

    @Override
    public KinzuOfTheBleakCovenCost copy() {
        return new KinzuOfTheBleakCovenCost(2);
    }

    @Override
    public void clearPaid() {
        super.clearPaid();
    }

}
