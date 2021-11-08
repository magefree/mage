package mage.cards.h;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author halljared
 */
public final class HarvestHand extends CardImpl {

    public HarvestHand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.secondSideCardClazz = mage.cards.s.ScroungedScythe.class;

        // When Harvest Hand dies, return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        this.addAbility(new DiesSourceTriggeredAbility(new HarvestHandReturnTransformedEffect()));
    }

    private HarvestHand(final HarvestHand card) {
        super(card);
    }

    @Override
    public HarvestHand copy() {
        return new HarvestHand(this);
    }
}

class HarvestHandReturnTransformedEffect extends OneShotEffect {

    HarvestHandReturnTransformedEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Return {this} to the battlefield transformed under your control";
    }

    private HarvestHandReturnTransformedEffect(final HarvestHandReturnTransformedEffect effect) {
        super(effect);
    }

    @Override
    public HarvestHandReturnTransformedEffect copy() {
        return new HarvestHandReturnTransformedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        if (!(sourceObject instanceof Card)) {
            return false;
        }
        game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
        controller.moveCards((Card) sourceObject, Zone.BATTLEFIELD, source, game);
        return true;
    }
}
