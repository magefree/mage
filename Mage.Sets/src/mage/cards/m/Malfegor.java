package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class Malfegor extends CardImpl {

    public Malfegor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Malfegor enters the battlefield, discard your hand. Each opponent sacrifices a creature for each card discarded this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MalfegorEffect(), false));

    }

    private Malfegor(final Malfegor card) {
        super(card);
    }

    @Override
    public Malfegor copy() {
        return new Malfegor(this);
    }
}

class MalfegorEffect extends OneShotEffect {

    public MalfegorEffect() {
        super(Outcome.Neutral);
        staticText = "discard your hand. Each opponent sacrifices a creature for each card discarded this way";
    }

    public MalfegorEffect(final MalfegorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int sacrificeNumber = controller.getHand().size();
        if (sacrificeNumber == 0) {
            return true;
        }
        new DiscardHandControllerEffect().apply(game, source);
        return new SacrificeOpponentsEffect(sacrificeNumber, StaticFilters.FILTER_CONTROLLED_CREATURE).apply(game, source);
    }

    @Override
    public MalfegorEffect copy() {
        return new MalfegorEffect(this);
    }
}
