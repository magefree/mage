package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
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

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever another nontoken creature you control dies, you may pay 2 Life and exile it.
        // If you do, create a token that's a copy of that card, except it’s 1/1 and has toxic 1.
        Effect effect = new KinzuOfTheBleakCovenEffect();
        this.addAbility(new DiesCreatureTriggeredAbility(effect,false, filter, true));

    }

    private KinzuOfTheBleakCoven(final KinzuOfTheBleakCoven card) {
        super(card);
    }

    @Override
    public KinzuOfTheBleakCoven copy() {
        return new KinzuOfTheBleakCoven(this);
    }
}

class KinzuOfTheBleakCovenEffect extends OneShotEffect {

    KinzuOfTheBleakCovenEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay 2 life and exile it. If you do, " +
                "create a token that's a copy of that creature" +
                ", except it's 1/1 and has toxic 1.";
    }

    private KinzuOfTheBleakCovenEffect(final KinzuOfTheBleakCovenEffect effect) {
        super(effect);
    }

    @Override
    public KinzuOfTheBleakCovenEffect copy() {
        return new KinzuOfTheBleakCovenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        Cost cost = new PayLifeCost(2);
        if (!cost.canPay(source, source, source.getControllerId(), game)
                || !player.chooseUse(Outcome.Benefit,"Pay 2 Life and Exile " + card.getName() + "?",source,game)
                || !cost.pay(source, game, source, source.getControllerId(), true)) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        return new CreateTokenCopyTargetEffect().setSavedPermanent(
                new PermanentCard(card, source.getControllerId(), game)
        ).setPermanentModifier((token) -> {
            token.setPower(1);                             // 1/1
            token.setToughness(1);
            token.addAbility(new ToxicAbility(1)); // Add Toxic (is additive)
        }).apply(game, source);
    }
}
