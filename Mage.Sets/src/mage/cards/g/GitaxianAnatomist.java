package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GitaxianAnatomist extends CardImpl {

    public GitaxianAnatomist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // When Gitaxian Anatomist enters the battlefield, you may tap it. If you do, proliferate.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoIfCostPaid(new ProliferateEffect(), new GitaxianAnatomistCost())
        ));
    }

    private GitaxianAnatomist(final GitaxianAnatomist card) {
        super(card);
    }

    @Override
    public GitaxianAnatomist copy() {
        return new GitaxianAnatomist(this);
    }
}

// TapSourceCost just does not work here as it is checking for permanent.canTap(game).
class GitaxianAnatomistCost extends CostImpl {

    public GitaxianAnatomistCost() {
        this.text = "tap it";
    }

    private GitaxianAnatomistCost(final GitaxianAnatomistCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            paid = permanent.tap(source, game);
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        return permanent != null && !permanent.isTapped();
    }

    @Override
    public GitaxianAnatomistCost copy() {
        return new GitaxianAnatomistCost(this);
    }
}
