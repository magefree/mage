package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.AdventureCard;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacesCard;
import mage.cards.ModalDoubleFacesCardHalf;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

/**
 * @author jeffwadsworth
 */
public final class EtherealValkyrie extends CardImpl {

    public EtherealValkyrie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Ethereal Valkyrie enters the battlefield or attacks, draw a card, then exile a card from your hand face down. It becomes foretold. Its foretell cost is its mana cost reduced by {2}.
        this.addAbility(new EtherealValkyrieTriggeredAbility(new EtherealValkyrieEffect()));

    }

    private EtherealValkyrie(final EtherealValkyrie card) {
        super(card);
    }

    @Override
    public EtherealValkyrie copy() {
        return new EtherealValkyrie(this);
    }
}

class EtherealValkyrieTriggeredAbility extends TriggeredAbilityImpl {

    EtherealValkyrieTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    EtherealValkyrieTriggeredAbility(final EtherealValkyrieTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EtherealValkyrieTriggeredAbility copy() {
        return new EtherealValkyrieTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                || event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent p = game.getPermanent(event.getSourceId());
        Permanent pETB = game.getPermanent(event.getTargetId());
        if (p != null
                && p.getId() == sourceId) {
            return true;
        }
        if (pETB != null
                && pETB.getId() == sourceId) {
            return true;
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever {this} enters the battlefield or attacks, ";
    }
}

class EtherealValkyrieEffect extends OneShotEffect {

    public EtherealValkyrieEffect() {
        super(Outcome.Benefit);
        this.staticText = "draw a card, then exile a card from your hand face down. It becomes foretold. Its foretell cost is its mana cost reduced by {2}";
    }

    public EtherealValkyrieEffect(final EtherealValkyrieEffect effect) {
        super(effect);
    }

    @Override
    public EtherealValkyrieEffect copy() {
        return new EtherealValkyrieEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.drawCards(1, source, game);
            TargetCardInHand targetCard = new TargetCardInHand(new FilterCard("card to exile face down. It becomes foretold."));
            if (controller.chooseTarget(Outcome.Benefit, targetCard, source, game)) {
                Card exileCard = game.getCard(targetCard.getFirstTarget());
                if (exileCard == null) {
                    return false;
                }
                // process Split, MDFC, and Adventure cards first
                // note that 'Foretell Cost' refers to the main card (left) and 'Foretell Split Cost' refers to the (right) card if it exists
                ForetellAbility foretellAbility = null;
                if (exileCard instanceof SplitCard) {
                    String leftHalfCost = CardUtil.reduceCost(((SplitCard) exileCard).getLeftHalfCard().getManaCost(), 2).getText();
                    String rightHalfCost = CardUtil.reduceCost(((SplitCard) exileCard).getRightHalfCard().getManaCost(), 2).getText();
                    game.getState().setValue(exileCard.getMainCard().getId().toString() + "Foretell Cost", leftHalfCost);
                    game.getState().setValue(exileCard.getMainCard().getId().toString() + "Foretell Split Cost", rightHalfCost);
                    foretellAbility = new ForetellAbility(exileCard, leftHalfCost, rightHalfCost);
                } else if (exileCard instanceof ModalDoubleFacesCard) {
                    ModalDoubleFacesCardHalf leftHalfCard = ((ModalDoubleFacesCard) exileCard).getLeftHalfCard();
                    if (!leftHalfCard.isLand(game)) {
                        String leftHalfCost = CardUtil.reduceCost(leftHalfCard.getManaCost(), 2).getText();
                        game.getState().setValue(exileCard.getMainCard().getId().toString() + "Foretell Cost", leftHalfCost);
                        ModalDoubleFacesCardHalf rightHalfCard = ((ModalDoubleFacesCard) exileCard).getRightHalfCard();
                        if (rightHalfCard.isLand(game)) {
                            foretellAbility = new ForetellAbility(exileCard, leftHalfCost);
                        } else {
                            String rightHalfCost = CardUtil.reduceCost(rightHalfCard.getManaCost(), 2).getText();
                            game.getState().setValue(exileCard.getMainCard().getId().toString() + "Foretell Split Cost", rightHalfCost);
                            foretellAbility = new ForetellAbility(exileCard, leftHalfCost, rightHalfCost);
                        }
                    }
                } else if (exileCard instanceof AdventureCard) {
                    String creatureCost = CardUtil.reduceCost(exileCard.getMainCard().getManaCost(), 2).getText();
                    String spellCost = CardUtil.reduceCost(((AdventureCard) exileCard).getSpellCard().getManaCost(), 2).getText();
                    game.getState().setValue(exileCard.getMainCard().getId().toString() + "Foretell Cost", creatureCost);
                    game.getState().setValue(exileCard.getMainCard().getId().toString() + "Foretell Split Cost", spellCost);
                    foretellAbility = new ForetellAbility(exileCard, creatureCost, spellCost);
                } else {
                    // normal card
                    String costText = CardUtil.reduceCost(exileCard.getManaCost(), 2).getText();
                    game.getState().setValue(exileCard.getId().toString() + "Foretell Cost", costText);
                    foretellAbility = new ForetellAbility(exileCard, costText);
                }
                // all done pre-processing so stick the foretell cost effect onto the main card
                // note that the card is not foretell'd into exile, it is put into exile and made foretold
                if (foretellAbility != null) {
                    // copy source and use it for the foretold effect on the exiled card
                    // bug #8673
                    Ability copiedSource = source.copy();
                    copiedSource.newId();
                    copiedSource.setSourceId(exileCard.getId());
                    game.getState().setValue(exileCard.getMainCard().getId().toString() + "Foretell Turn Number", game.getTurnNum());
                    UUID exileId = CardUtil.getExileZoneId(exileCard.getMainCard().getId().toString() + "foretellAbility", game);
                    controller.moveCardsToExile(exileCard, source, game, true, exileId, " Foretell Turn Number: " + game.getTurnNum());
                    exileCard.setFaceDown(true, game);
                    foretellAbility.setSourceId(exileCard.getId());
                    foretellAbility.setControllerId(exileCard.getOwnerId());
                    game.getState().addOtherAbility(exileCard, foretellAbility);
                    foretellAbility.activate(game, true);
                    ContinuousEffect effect = foretellAbility.new ForetellAddCostEffect(new MageObjectReference(exileCard, game));
                    game.addEffect(effect, copiedSource);
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.FORETOLD, exileCard.getId(), null, null));
                    return true;
                }
            }
        }
        return false;
    }
}
