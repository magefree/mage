package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.SpellsCostModificationThatTargetSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author halljared
 */
public final class AccursedWitch extends CardImpl {

    public AccursedWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        this.secondSideCardClazz = mage.cards.i.InfectiousCurse.class;

        // Spells your opponents cast that target Accursed Witch cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new SpellsCostModificationThatTargetSourceEffect(-1, new FilterCard("Spells"), TargetController.OPPONENT))
        );

        // When Accursed Witch dies, return it to the battlefield transformed under your control attached to target opponent.
        this.addAbility(new TransformAbility());
        Ability ability = new DiesSourceTriggeredAbility(new AccursedWitchReturnTransformedEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private AccursedWitch(final AccursedWitch card) {
        super(card);
    }

    @Override
    public AccursedWitch copy() {
        return new AccursedWitch(this);
    }
}

class AccursedWitchReturnTransformedEffect extends OneShotEffect {

    AccursedWitchReturnTransformedEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return it to the battlefield transformed under your control attached to target opponent";
    }

    private AccursedWitchReturnTransformedEffect(final AccursedWitchReturnTransformedEffect effect) {
        super(effect);
    }

    @Override
    public AccursedWitchReturnTransformedEffect copy() {
        return new AccursedWitchReturnTransformedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player attachTo = game.getPlayer(targetPointer.getFirst(game, source));
        if (controller == null || !(game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) || attachTo == null) {
            return false;
        }

        Card card = game.getCard(source.getSourceId());
        if (card == null) {
            return false;
        }

        game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
        game.getState().setValue("attachTo:" + source.getSourceId(), attachTo.getId());
        if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
            attachTo.addAttachment(card.getId(), source, game);
        }
        return true;
    }
}
