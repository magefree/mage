package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class DeceiverOfForm extends CardImpl {

    public DeceiverOfForm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{C}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // At the beginning of combat on your turn, reveal the top card of your library.
        // If a creature card is revealed this way, you may have creatures you control other than Deceiver of Form becomes copies of that card until end of turn.
        // You may put that card on the bottom of your library.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new DeceiverOfFormEffect(), TargetController.YOU, false));
    }

    private DeceiverOfForm(final DeceiverOfForm card) {
        super(card);
    }

    @Override
    public DeceiverOfForm copy() {
        return new DeceiverOfForm(this);
    }
}

class DeceiverOfFormEffect extends OneShotEffect {

    public DeceiverOfFormEffect() {
        super(Outcome.Copy);
        this.staticText = "reveal the top card of your library. If a creature card is revealed this way, you may have creatures you control other than Deceiver of Form becomes copies of that card until end of turn. You may put that card on the bottom of your library";
    }

    public DeceiverOfFormEffect(final DeceiverOfFormEffect effect) {
        super(effect);
    }

    @Override
    public DeceiverOfFormEffect copy() {
        return new DeceiverOfFormEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card copyFromCard = null;
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null
                && sourceObject != null) {
            Card cardFromTop = controller.getLibrary().getFromTop(game);
            if (cardFromTop != null) {
                Cards cards = new CardsImpl(cardFromTop);
                controller.revealCards(sourceObject.getIdName(), cards, game);
                if (cardFromTop.isCreature(game)) {
                    if (controller.chooseUse(outcome, "Let creatures you control other than "
                            + sourceObject.getLogName() + " becomes copies of " + cardFromTop.getLogName() + " until end of turn?", source, game)) {
                        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, controller.getId(), game)) {
                            if (!permanent.getId().equals(sourceObject.getId())) {
                                copyFromCard = cardFromTop;
                                // handle MDFC
                                if (cardFromTop instanceof ModalDoubleFacesCard
                                        && ((ModalDoubleFacesCard) cardFromTop).getLeftHalfCard().isCreature(game)) {
                                    copyFromCard = ((ModalDoubleFacesCard) cardFromTop).getLeftHalfCard();
                                }
                                Permanent newBluePrint = null;
                                newBluePrint = new PermanentCard(copyFromCard, source.getControllerId(), game);
                                newBluePrint.assignNewId();
                                CopyEffect copyEffect = new CopyEffect(Duration.EndOfTurn, newBluePrint, permanent.getId());
                                copyEffect.newId();
                                Ability newAbility = source.copy();
                                copyEffect.init(newAbility, game);
                                game.addEffect(copyEffect, newAbility);
                            }
                        }
                    }
                }
                if (controller.chooseUse(outcome, "Move " + copyFromCard.getLogName() + " to the bottom of your library?", source, game)) {
                    controller.moveCardToLibraryWithInfo(copyFromCard, source, game, Zone.LIBRARY, false, true);
                }
            }
            return true;
        }
        return false;
    }
}
