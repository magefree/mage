package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801, jeffwadsworth
 */
public final class EdginLarcenousLutenist extends CardImpl {

    public EdginLarcenousLutenist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Each nonland card in your hand without foretell has foretell. Its foretell cost is equal to its mana cost reduced by {2}.
        this.addAbility(new SimpleStaticAbility(new EdginLarcenousLutenistEffect()));

        // Whenever you cast your second spell each turn, goad target creature an opponent controls.
        Ability ability = new CastSecondSpellTriggeredAbility(new GoadTargetEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private EdginLarcenousLutenist(final EdginLarcenousLutenist card) {
        super(card);
    }

    @Override
    public EdginLarcenousLutenist copy() {
        return new EdginLarcenousLutenist(this);
    }
}

class EdginLarcenousLutenistEffect extends ContinuousEffectImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard();

    static {
        filter.add(Predicates.not(new AbilityPredicate(ForetellAbility.class)));
    }

    EdginLarcenousLutenistEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "Each nonland card in your hand without foretell has foretell. Its foretell cost is equal to its mana cost reduced by {2}";
    }

    private EdginLarcenousLutenistEffect(final EdginLarcenousLutenistEffect effect) {
        super(effect);
    }

    @Override
    public EdginLarcenousLutenistEffect copy() {
        return new EdginLarcenousLutenistEffect(this);
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
