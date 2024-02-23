package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.BoarToken;
import mage.game.permanent.token.Token;

import java.util.UUID;

public final class StampedeSurfer extends CardImpl {

    public StampedeSurfer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R/G}{R/G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new mage.MageInt(4);
        this.toughness = new mage.MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Stampede Surfer attacks, for each opponent, you create a 2/2 green Boar creature token that's tapped and attacking that opponent.
        this.addAbility(new AttacksTriggeredAbility(new StampedeSurferEffect()));
    }

    private StampedeSurfer(final StampedeSurfer card) {
        super(card);
    }

    @Override
    public StampedeSurfer copy() {
        return new StampedeSurfer(this);
    }
}

class StampedeSurferEffect extends OneShotEffect {

    private static final Token token = new BoarToken();

    StampedeSurferEffect() {
        super(Outcome.Benefit);
        staticText = "for each opponent, you create a 2/2 green Boar creature token that's tapped and attacking that opponent";
    }

    private StampedeSurferEffect(final StampedeSurferEffect effect) {
        super(effect);
    }

    @Override
    public StampedeSurferEffect copy() {
        return new StampedeSurferEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            token.putOntoBattlefield(1, game, source, source.getControllerId(), true, true, playerId);
        }
        return true;
    }
}
