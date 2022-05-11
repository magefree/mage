package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author weirddan455
 */
public final class SpeakeasyServer extends CardImpl {

    public SpeakeasyServer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Speakeasy Server enters the battlefield, you gain 1 life for each other creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SpeakeasyServerEffect()));
    }

    private SpeakeasyServer(final SpeakeasyServer card) {
        super(card);
    }

    @Override
    public SpeakeasyServer copy() {
        return new SpeakeasyServer(this);
    }
}

class SpeakeasyServerEffect extends OneShotEffect {

    public SpeakeasyServerEffect() {
        super(Outcome.GainLife);
        this.staticText = "you gain 1 life for each other creature you control";
    }

    private SpeakeasyServerEffect(final SpeakeasyServerEffect effect) {
        super(effect);
    }

    @Override
    public SpeakeasyServerEffect copy() {
        return new SpeakeasyServerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID controllerId = source.getControllerId();
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }
        controller.gainLife(game.getBattlefield().count(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, controllerId, source, game), game, source);
        return true;
    }
}
