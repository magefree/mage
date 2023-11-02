package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author noxx
 */
public final class Fettergeist extends CardImpl {

    public Fettergeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, sacrifice Fettergeist unless you pay {1} for each other creature you control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new FettergeistUnlessPaysEffect(), TargetController.YOU, false));

    }

    private Fettergeist(final Fettergeist card) {
        super(card);
    }

    @Override
    public Fettergeist copy() {
        return new Fettergeist(this);
    }
}

class FettergeistUnlessPaysEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    public FettergeistUnlessPaysEffect() {
        super(Outcome.Sacrifice);
        staticText = "sacrifice {this} unless you pay {1} for each other creature you control.";
    }

    private FettergeistUnlessPaysEffect(final FettergeistUnlessPaysEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            PermanentsOnBattlefieldCount amount = new PermanentsOnBattlefieldCount(filter, 1);
            int count = amount.calculate(game, source, this);
            if (player.chooseUse(Outcome.Benefit, "Pay {" + count + "}?  Or " + permanent.getName() + " will be sacrificed.", source, game)) {
                Cost cost = ManaUtil.createManaCost(count, false);
                if (cost.pay(source, game, source, source.getControllerId(), false)) {
                    return true;
                }
            }
            permanent.sacrifice(source, game);
            return true;
        }
        return false;
    }

    @Override
    public FettergeistUnlessPaysEffect copy() {
        return new FettergeistUnlessPaysEffect(this);
    }

}

