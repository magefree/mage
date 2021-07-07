package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WestgateRegent extends CardImpl {

    public WestgateRegent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward — Discard a card.
        this.addAbility(new WardAbility(new DiscardCardCost()));

        // Whenever Westgate Regent deals combat damage to a player, put that many +1/+1 counters on it.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new WestgateRegentEffect(), false, true
        ));
    }

    private WestgateRegent(final WestgateRegent card) {
        super(card);
    }

    @Override
    public WestgateRegent copy() {
        return new WestgateRegent(this);
    }
}

class WestgateRegentEffect extends OneShotEffect {

    WestgateRegentEffect() {
        super(Outcome.Benefit);
        staticText = "put that many +1/+1 counters on it";
    }

    private WestgateRegentEffect(final WestgateRegentEffect effect) {
        super(effect);
    }

    @Override
    public WestgateRegentEffect copy() {
        return new WestgateRegentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        int damage = (Integer) getValue("damage");
        if (permanent == null || damage < 1) {
            return false;
        }
        return permanent.addCounters(
                CounterType.P1P1.createInstance(damage),
                source.getControllerId(), source, game
        );
    }
}
