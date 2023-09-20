package mage.cards.c;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;

/**
 *
 * @author TheElk801
 */
public final class CentaurPeacemaker extends CardImpl {

    public CentaurPeacemaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Centaur Mediator enters the battlefield, each player gains 4 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CentaurMediatorEffect()
        ));
    }

    private CentaurPeacemaker(final CentaurPeacemaker card) {
        super(card);
    }

    @Override
    public CentaurPeacemaker copy() {
        return new CentaurPeacemaker(this);
    }
}

class CentaurMediatorEffect extends OneShotEffect {

    public CentaurMediatorEffect() {
        super(Outcome.GainLife);
        staticText = "each player gains 4 life.";
    }

    private CentaurMediatorEffect(final CentaurMediatorEffect effect) {
        super(effect);
    }

    @Override
    public CentaurMediatorEffect copy() {
        return new CentaurMediatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getState().getPlayersInRange(
                source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .forEachOrdered(player -> player.gainLife(4, game, source));
        return true;
    }

}
