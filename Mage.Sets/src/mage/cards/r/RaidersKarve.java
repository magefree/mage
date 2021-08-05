package mage.cards.r;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RaidersKarve extends CardImpl {

    public RaidersKarve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Raiders' Karve attacks, look at the top card of your library. If it's a land card, you may put it onto the battlefield tapped.
        this.addAbility(new AttacksTriggeredAbility(new RaidersKarveEffect(), false));

        // Crew 3
        this.addAbility(new CrewAbility(3));
    }

    private RaidersKarve(final RaidersKarve card) {
        super(card);
    }

    @Override
    public RaidersKarve copy() {
        return new RaidersKarve(this);
    }
}

class RaidersKarveEffect extends OneShotEffect {

    RaidersKarveEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. " +
                "If it's a land card, you may put it onto the battlefield tapped";
    }

    private RaidersKarveEffect(final RaidersKarveEffect effect) {
        super(effect);
    }

    @Override
    public RaidersKarveEffect copy() {
        return new RaidersKarveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        if (card == null) {
            return true;
        }
        controller.lookAtCards(sourceObject.getIdName(), new CardsImpl(card), game);
        if (!card.isLand(game)) {
            return true;
        }
        String message = "Put " + card.getLogName() + " onto the battlefield tapped?";
        if (controller.chooseUse(Outcome.PutLandInPlay, message, source, game)) {
            controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
        }
        return true;
    }
}
