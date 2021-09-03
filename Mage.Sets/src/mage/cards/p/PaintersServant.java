package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacesCard;
import mage.cards.ModalDoubleFacesCardHalf;
import mage.cards.SplitCard;
import mage.cards.SplitCardHalf;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.CommandObject;
import mage.game.command.Commander;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class PaintersServant extends CardImpl {

    public PaintersServant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        this.subtype.add(SubType.SCARECROW);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // As Painter's Servant enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Detriment)));

        // All cards that aren't on the battlefield, spells, and permanents are the chosen color in addition to their other colors.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PaintersServantEffect()));
    }

    private PaintersServant(final PaintersServant card) {
        super(card);
    }

    @Override
    public PaintersServant copy() {
        return new PaintersServant(this);
    }
}

class PaintersServantEffect extends ContinuousEffectImpl {

    public PaintersServantEffect() {
        super(Duration.WhileOnBattlefield, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Benefit);
        staticText = "All cards that aren't on the battlefield, spells, and permanents are the chosen color in addition to their other colors";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
            if (color == null) {
                return false;
            }
            for (Permanent perm : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
                perm.getColor(game).addColor(color);
            }
            // Stack
            for (MageObject object : game.getStack()) {
                if (object instanceof Spell) {
                    game.getState().getCreateMageObjectAttribute(object, game).getColor().addColor(color);
                    Card card = ((Spell) object).getCard();
                    game.getState().getCreateMageObjectAttribute(card, game).getColor().addColor(color);
                }
            }
            // Exile
            for (Card card : game.getExile().getAllCards(game)) {
                // handle MDFC
                if (card instanceof ModalDoubleFacesCard) {
                    ModalDoubleFacesCardHalf leftHalfCard = ((ModalDoubleFacesCard) card).getLeftHalfCard();
                    ModalDoubleFacesCardHalf rightHalfCard = ((ModalDoubleFacesCard) card).getRightHalfCard();
                    game.getState().getCreateMageObjectAttribute(leftHalfCard, game).getColor().addColor(color);
                    game.getState().getCreateMageObjectAttribute(rightHalfCard, game).getColor().addColor(color);
                }
                // handle Split-Cards
                if (card instanceof SplitCard) {
                    SplitCardHalf leftHalfCard = ((SplitCard) card).getLeftHalfCard();
                    SplitCardHalf rightHalfCard = ((SplitCard) card).getRightHalfCard();
                    game.getState().getCreateMageObjectAttribute(leftHalfCard, game).getColor().addColor(color);
                    game.getState().getCreateMageObjectAttribute(rightHalfCard, game).getColor().addColor(color);
                }
                game.getState().getCreateMageObjectAttribute(card, game).getColor().addColor(color);
            }
            // Command
            for (CommandObject commandObject : game.getState().getCommand()) {
                if (commandObject instanceof Commander) {
                    Card card = game.getCard(((Commander) commandObject).getId());
                    if (card != null) {
                        // handle MDFC
                        if (card instanceof ModalDoubleFacesCard) {
                            ModalDoubleFacesCardHalf leftHalfCard = ((ModalDoubleFacesCard) card).getLeftHalfCard();
                            ModalDoubleFacesCardHalf rightHalfCard = ((ModalDoubleFacesCard) card).getRightHalfCard();
                            game.getState().getCreateMageObjectAttribute(leftHalfCard, game).getColor().addColor(color);
                            game.getState().getCreateMageObjectAttribute(rightHalfCard, game).getColor().addColor(color);
                        }
                        // handle Split-Cards
                        if (card instanceof SplitCard) {
                            SplitCardHalf leftHalfCard = ((SplitCard) card).getLeftHalfCard();
                            SplitCardHalf rightHalfCard = ((SplitCard) card).getRightHalfCard();
                            game.getState().getCreateMageObjectAttribute(leftHalfCard, game).getColor().addColor(color);
                            game.getState().getCreateMageObjectAttribute(rightHalfCard, game).getColor().addColor(color);
                        }
                        game.getState().getCreateMageObjectAttribute(card, game).getColor().addColor(color);
                    }
                }
            }

            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    // Hand
                    for (Card card : player.getHand().getCards(game)) {
                        // handle MDFC
                        if (card instanceof ModalDoubleFacesCard) {
                            ModalDoubleFacesCardHalf leftHalfCard = ((ModalDoubleFacesCard) card).getLeftHalfCard();
                            ModalDoubleFacesCardHalf rightHalfCard = ((ModalDoubleFacesCard) card).getRightHalfCard();
                            game.getState().getCreateMageObjectAttribute(leftHalfCard, game).getColor().addColor(color);
                            game.getState().getCreateMageObjectAttribute(rightHalfCard, game).getColor().addColor(color);
                        }
                        // handle Split-Cards
                        if (card instanceof SplitCard) {
                            SplitCardHalf leftHalfCard = ((SplitCard) card).getLeftHalfCard();
                            SplitCardHalf rightHalfCard = ((SplitCard) card).getRightHalfCard();
                            game.getState().getCreateMageObjectAttribute(leftHalfCard, game).getColor().addColor(color);
                            game.getState().getCreateMageObjectAttribute(rightHalfCard, game).getColor().addColor(color);
                        }
                        game.getState().getCreateMageObjectAttribute(card.getMainCard(), game).getColor().addColor(color);
                        if (card.getSecondCardFace() != null) {
                            game.getState().getCreateMageObjectAttribute(card.getSecondCardFace(), game).getColor().addColor(color);
                        }
                    }
                    // Library
                    for (Card card : player.getLibrary().getCards(game)) {
                        // handle MDFC
                        if (card instanceof ModalDoubleFacesCard) {
                            ModalDoubleFacesCardHalf leftHalfCard = ((ModalDoubleFacesCard) card).getLeftHalfCard();
                            ModalDoubleFacesCardHalf rightHalfCard = ((ModalDoubleFacesCard) card).getRightHalfCard();
                            game.getState().getCreateMageObjectAttribute(leftHalfCard, game).getColor().addColor(color);
                            game.getState().getCreateMageObjectAttribute(rightHalfCard, game).getColor().addColor(color);
                        }
                        // handle Split-Cards
                        if (card instanceof SplitCard) {
                            SplitCardHalf leftHalfCard = ((SplitCard) card).getLeftHalfCard();
                            SplitCardHalf rightHalfCard = ((SplitCard) card).getRightHalfCard();
                            game.getState().getCreateMageObjectAttribute(leftHalfCard, game).getColor().addColor(color);
                            game.getState().getCreateMageObjectAttribute(rightHalfCard, game).getColor().addColor(color);
                        }
                        game.getState().getCreateMageObjectAttribute(card, game).getColor().addColor(color);
                    }
                    // Graveyard
                    for (Card card : player.getGraveyard().getCards(game)) {
                        // handle MDFC
                        if (card instanceof ModalDoubleFacesCard) {
                            ModalDoubleFacesCardHalf leftHalfCard = ((ModalDoubleFacesCard) card).getLeftHalfCard();
                            ModalDoubleFacesCardHalf rightHalfCard = ((ModalDoubleFacesCard) card).getRightHalfCard();
                            game.getState().getCreateMageObjectAttribute(leftHalfCard, game).getColor().addColor(color);
                            game.getState().getCreateMageObjectAttribute(rightHalfCard, game).getColor().addColor(color);
                        }
                        // handle Split-Cards
                        if (card instanceof SplitCard) {
                            SplitCardHalf leftHalfCard = ((SplitCard) card).getLeftHalfCard();
                            SplitCardHalf rightHalfCard = ((SplitCard) card).getRightHalfCard();
                            game.getState().getCreateMageObjectAttribute(leftHalfCard, game).getColor().addColor(color);
                            game.getState().getCreateMageObjectAttribute(rightHalfCard, game).getColor().addColor(color);
                        }
                        game.getState().getCreateMageObjectAttribute(card, game).getColor().addColor(color);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public PaintersServantEffect copy() {
        return new PaintersServantEffect(this);
    }

    private PaintersServantEffect(PaintersServantEffect effect) {
        super(effect);
    }

}
