package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ForetellSourceControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.*;
import mage.constants.SubType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public final class DreamDevourer extends CardImpl {

    public DreamDevourer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Each nonland card in your hand without foretell has foretell. Its foretell cost is equal to its mana cost reduced by 2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DreamDevourerAddAbilityEffect()));

        // Whenever you foretell a card, Dream Devourer gets +2/+0 until end of turn.
        this.addAbility(new ForetellSourceControllerTriggeredAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn)));

    }

    private DreamDevourer(final DreamDevourer card) {
        super(card);
    }

    @Override
    public DreamDevourer copy() {
        return new DreamDevourer(this);
    }
}

class DreamDevourerAddAbilityEffect extends ContinuousEffectImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard();

    static {
        filter.add(Predicates.not(new AbilityPredicate(ForetellAbility.class)));
    }

    DreamDevourerAddAbilityEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "Each nonland card in your hand without foretell has foretell. Its foretell cost is equal to its mana cost reduced by {2}";
    }

    private DreamDevourerAddAbilityEffect(final DreamDevourerAddAbilityEffect effect) {
        super(effect);
    }

    @Override
    public DreamDevourerAddAbilityEffect copy() {
        return new DreamDevourerAddAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (Card card : controller.getHand().getCards(filter, game)) {
            ForetellAbility foretellAbility = null;
            if (card instanceof SplitCard) {
                String leftHalfCost = CardUtil.reduceCost(((SplitCard) card).getLeftHalfCard().getManaCost(), 2).getText();
                String rightHalfCost = CardUtil.reduceCost(((SplitCard) card).getRightHalfCard().getManaCost(), 2).getText();
                foretellAbility = new ForetellAbility(card, leftHalfCost, rightHalfCost);
            } else if (card instanceof ModalDoubleFacedCard) {
                ModalDoubleFacedCardHalf leftHalfCard = ((ModalDoubleFacedCard) card).getLeftHalfCard();
                // If front side of MDFC is land, do nothing as Dream Devourer does not apply to lands
                // MDFC cards in hand are considered lands if front side is land
                if (!leftHalfCard.isLand(game)) {
                    String leftHalfCost = CardUtil.reduceCost(leftHalfCard.getManaCost(), 2).getText();
                    ModalDoubleFacedCardHalf rightHalfCard = ((ModalDoubleFacedCard) card).getRightHalfCard();
                    if (rightHalfCard.isLand(game)) {
                        foretellAbility = new ForetellAbility(card, leftHalfCost);
                    } else {
                        String rightHalfCost = CardUtil.reduceCost(rightHalfCard.getManaCost(), 2).getText();
                        foretellAbility = new ForetellAbility(card, leftHalfCost, rightHalfCost);
                    }
                }
            } else if (card instanceof AdventureCard) {
                String creatureCost = CardUtil.reduceCost(card.getMainCard().getManaCost(), 2).getText();
                String spellCost = CardUtil.reduceCost(((AdventureCard) card).getSpellCard().getManaCost(), 2).getText();
                foretellAbility = new ForetellAbility(card, creatureCost, spellCost);
            } else {
                String costText = CardUtil.reduceCost(card.getManaCost(), 2).getText();
                foretellAbility = new ForetellAbility(card, costText);
            }
            if (foretellAbility != null) {
                foretellAbility.setSourceId(card.getId());
                foretellAbility.setControllerId(card.getOwnerId());
                game.getState().addOtherAbility(card, foretellAbility);
            }
        }
        return true;
    }
}
