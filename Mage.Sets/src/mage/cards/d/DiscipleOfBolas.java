package mage.cards.d;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class DiscipleOfBolas extends CardImpl {

    public DiscipleOfBolas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Disciple of Bolas enters the battlefield, sacrifice another creature. You gain X life and draw X cards, where X is that creature's power.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DiscipleOfBolasEffect()));
    }

    private DiscipleOfBolas(final DiscipleOfBolas card) {
        super(card);
    }

    @Override
    public DiscipleOfBolas copy() {
        return new DiscipleOfBolas(this);
    }
}

class DiscipleOfBolasEffect extends OneShotEffect {

    DiscipleOfBolasEffect() {
        super(Outcome.Benefit);
        this.staticText = "sacrifice another creature. You gain X life and draw X cards, where X is that creature's power";
    }

    private DiscipleOfBolasEffect(final DiscipleOfBolasEffect effect) {
        super(effect);
    }

    @Override
    public DiscipleOfBolasEffect copy() {
        return new DiscipleOfBolasEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        SacrificeTargetCost cost = new SacrificeTargetCost(StaticFilters.FILTER_ANOTHER_CREATURE);
        if (!cost.canPay(source, source, source.getControllerId(), game)
                || !cost.pay(source, game, source, source.getControllerId(), true)) {
            return false;
        }
        int power = cost
                .getPermanents()
                .stream()
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .sum();
        if (power > 0) {
            controller.gainLife(power, game, source);
            controller.drawCards(power, source, game);
        }
        return true;
    }
}
