package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MilesTailsPrower extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.VEHICLE);

    public MilesTailsPrower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a Vehicle you control enters, draw a card if it has flying. Otherwise, put a flying counter on it.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new MilesTailsProwerEffect(), filter, false, SetTargetPointer.PERMANENT
        ));
    }

    private MilesTailsPrower(final MilesTailsPrower card) {
        super(card);
    }

    @Override
    public MilesTailsPrower copy() {
        return new MilesTailsPrower(this);
    }
}

class MilesTailsProwerEffect extends OneShotEffect {

    MilesTailsProwerEffect() {
        super(Outcome.Benefit);
        staticText = "draw a card if it has flying. Otherwise, put a flying counter on it";
    }

    private MilesTailsProwerEffect(final MilesTailsProwerEffect effect) {
        super(effect);
    }

    @Override
    public MilesTailsProwerEffect copy() {
        return new MilesTailsProwerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) getValue("permanentEnteringBattlefield");
        if (permanent == null) {
            return false;
        }
        if (permanent.hasAbility(FlyingAbility.getInstance(), game)) {
            return Optional
                    .ofNullable(source)
                    .map(Controllable::getControllerId)
                    .map(game::getPlayer)
                    .filter(player -> player.drawCards(1, source, game) > 0)
                    .isPresent();
        }
        return Optional
                .ofNullable(getTargetPointer().getFirstTargetPermanentOrLKI(game, source))
                .filter(p -> p.addCounters(CounterType.FLYING.createInstance(), source, game))
                .isPresent();
    }
}
