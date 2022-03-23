package mage.cards.j;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.common.MagecraftAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.cards.ModalDoubleFacesCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;
import mage.abilities.costs.Costs;
import mage.cards.AdventureCard;
import mage.cards.ModalDoubleFacesCardHalf;
import mage.cards.SplitCard;
import mage.cards.SplitCardHalf;

/**
 * @author TheElk801
 */
public final class JadziOracleOfArcavios extends ModalDoubleFacesCard {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, ComparisonType.MORE_THAN, 7
    );

    public JadziOracleOfArcavios(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WIZARD}, "{6}{U}{U}",
                "Journey to the Oracle",
                new CardType[]{CardType.SORCERY}, new SubType[]{}, "{2}{G}{G}"
        );

        // 1.
        // Jadzi, Oracle of Arcavios
        // Legendary Creature - Human Wizard
        this.getLeftHalfCard().addSuperType(SuperType.LEGENDARY);
        this.getLeftHalfCard().setPT(5, 5);

        // Discard a card: Return Jadzi, Oracle of Arcavios to its owner's hand.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(
                new ReturnToHandSourceEffect(true), new DiscardCardCost()
        ));

        // Magecraft — Whenever you cast or copy an instant or sorcery spell, reveal the top card of your library. If it's a nonland card, you may cast it by paying {1} rather than paying its mana cost. If it's a land card, put it onto the battlefield.
        this.getLeftHalfCard().addAbility(new MagecraftAbility(new JadziOracleOfArcaviosEffect()));

        // 1.
        // Journey to the Oracle
        // Sorcery
        // You may put any number of land cards from your hand onto the battlefield. Then if you control eight or more lands, you may discard a card. If you do, return Journey to the Oracle to it owner's hand.
        this.getRightHalfCard().getSpellAbility().addEffect(new JourneyToTheOracleEffect());
        this.getRightHalfCard().getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DoIfCostPaid(
                        new ReturnToHandSourceEffect(), new DiscardCardCost()
                ), condition, "Then if you control eight or more lands, "
                + "you may discard a card. If you do, return {this} to its owner's hand."
        ));
        this.getRightHalfCard().getSpellAbility().addHint(LandsYouControlHint.instance);
    }

    private JadziOracleOfArcavios(final JadziOracleOfArcavios card) {
        super(card);
    }

    @Override
    public JadziOracleOfArcavios copy() {
        return new JadziOracleOfArcavios(this);
    }
}

class JadziOracleOfArcaviosEffect extends OneShotEffect {

    JadziOracleOfArcaviosEffect() {
        super(Outcome.Benefit);
        staticText = "reveal the top card of your library. If it's a nonland card, you may cast it "
                + "by paying {1} rather than paying its mana cost. If it's a land card, put it onto the battlefield";
    }

    private JadziOracleOfArcaviosEffect(final JadziOracleOfArcaviosEffect effect) {
        super(effect);
    }

    @Override
    public JadziOracleOfArcaviosEffect copy() {
        return new JadziOracleOfArcaviosEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        controller.revealCards(source, new CardsImpl(card), game);
        if (card.isLand(game)) {
            // this is a bit wierd in game, though it works fine
            // note that MDFC land cards are handled differently: they can't be moved
            return controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        }

        // query player
        if (!controller.chooseUse(outcome, "Cast " + card.getName() + " by paying {1}?", source, game)) {
            return false;
        }

        // handle split-cards
        if (card instanceof SplitCard) {
            SplitCardHalf leftHalfCard = ((SplitCard) card).getLeftHalfCard();
            SplitCardHalf rightHalfCard = ((SplitCard) card).getRightHalfCard();
            // get additional cost if any
            Costs additionalCostsLeft = leftHalfCard.getSpellAbility().getCosts();
            Costs additionalCostsRight = rightHalfCard.getSpellAbility().getCosts();
            // set alternative cost and any additional cost
            controller.setCastSourceIdWithAlternateMana(leftHalfCard.getId(), new ManaCostsImpl<>("{1}"), additionalCostsLeft);
            controller.setCastSourceIdWithAlternateMana(rightHalfCard.getId(), new ManaCostsImpl<>("{1}"), additionalCostsRight);
            // allow the card to be cast
            game.getState().setValue("PlayFromNotOwnHandZone" + leftHalfCard.getId(), Boolean.TRUE);
            game.getState().setValue("PlayFromNotOwnHandZone" + rightHalfCard.getId(), Boolean.TRUE);
        }

        // handle MDFC
        if (card instanceof ModalDoubleFacesCard) {
            ModalDoubleFacesCardHalf leftHalfCard = ((ModalDoubleFacesCard) card).getLeftHalfCard();
            ModalDoubleFacesCardHalf rightHalfCard = ((ModalDoubleFacesCard) card).getRightHalfCard();
            // some MDFC cards are lands.  IE: sea gate restoration
            if (!leftHalfCard.isLand(game)) {
                // get additional cost if any
                Costs additionalCostsMDFCLeft = leftHalfCard.getSpellAbility().getCosts();
                // set alternative cost and any additional cost
                controller.setCastSourceIdWithAlternateMana(leftHalfCard.getId(), new ManaCostsImpl<>("{1}"), additionalCostsMDFCLeft);
            }
            if (!rightHalfCard.isLand(game)) {
                // get additional cost if any
                Costs additionalCostsMDFCRight = rightHalfCard.getSpellAbility().getCosts();
                // set alternative cost and any additional cost
                controller.setCastSourceIdWithAlternateMana(rightHalfCard.getId(), new ManaCostsImpl<>("{1}"), additionalCostsMDFCRight);
            }
            // allow the card to be cast
            game.getState().setValue("PlayFromNotOwnHandZone" + leftHalfCard.getId(), Boolean.TRUE);
            game.getState().setValue("PlayFromNotOwnHandZone" + rightHalfCard.getId(), Boolean.TRUE);
        }

        // handle adventure cards
        if (card instanceof AdventureCard) {
            Card creatureCard = card.getMainCard();
            Card spellCard = ((AdventureCard) card).getSpellCard();
            // get additional cost if any
            Costs additionalCostsCreature = creatureCard.getSpellAbility().getCosts();
            Costs additionalCostsSpellCard = spellCard.getSpellAbility().getCosts();
            // set alternative cost and any additional cost
            controller.setCastSourceIdWithAlternateMana(creatureCard.getId(), new ManaCostsImpl<>("{1}"), additionalCostsCreature);
            controller.setCastSourceIdWithAlternateMana(spellCard.getId(), new ManaCostsImpl<>("{1}"), additionalCostsSpellCard);
            // allow the card to be cast
            game.getState().setValue("PlayFromNotOwnHandZone" + creatureCard.getId(), Boolean.TRUE);
            game.getState().setValue("PlayFromNotOwnHandZone" + spellCard.getId(), Boolean.TRUE);
        }

        // normal card
        if (!(card instanceof SplitCard)
                || !(card instanceof ModalDoubleFacesCard)
                || !(card instanceof AdventureCard)) {
            // get additional cost if any
            Costs additionalCostsNormalCard = card.getSpellAbility().getCosts();
            controller.setCastSourceIdWithAlternateMana(card.getMainCard().getId(), new ManaCostsImpl<>("{1}"), additionalCostsNormalCard);
        }

        // cast it
        controller.cast(controller.chooseAbilityForCast(card.getMainCard(), game, false),
                game, false, new ApprovingObject(source, game));

        // turn off effect after cast on every possible card-face
        if (card instanceof SplitCard) {
            SplitCardHalf leftHalfCard = ((SplitCard) card).getLeftHalfCard();
            SplitCardHalf rightHalfCard = ((SplitCard) card).getRightHalfCard();
            game.getState().setValue("PlayFromNotOwnHandZone" + leftHalfCard.getId(), null);
            game.getState().setValue("PlayFromNotOwnHandZone" + rightHalfCard.getId(), null);
        }
        if (card instanceof ModalDoubleFacesCard) {
            ModalDoubleFacesCardHalf leftHalfCard = ((ModalDoubleFacesCard) card).getLeftHalfCard();
            ModalDoubleFacesCardHalf rightHalfCard = ((ModalDoubleFacesCard) card).getRightHalfCard();
            game.getState().setValue("PlayFromNotOwnHandZone" + leftHalfCard.getId(), null);
            game.getState().setValue("PlayFromNotOwnHandZone" + rightHalfCard.getId(), null);
        }
        if (card instanceof AdventureCard) {
            Card creatureCard = card.getMainCard();
            Card spellCard = ((AdventureCard) card).getSpellCard();
            game.getState().setValue("PlayFromNotOwnHandZone" + creatureCard.getId(), null);
            game.getState().setValue("PlayFromNotOwnHandZone" + spellCard.getId(), null);
        }
        // turn off effect on a normal card
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);

        return true;
    }
}

class JourneyToTheOracleEffect extends OneShotEffect {

    JourneyToTheOracleEffect() {
        super(Outcome.Benefit);
        staticText = "You may put any number of land cards from your hand onto the battlefield";
    }

    private JourneyToTheOracleEffect(final JourneyToTheOracleEffect effect) {
        super(effect);
    }

    @Override
    public JourneyToTheOracleEffect copy() {
        return new JourneyToTheOracleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInHand target = new TargetCardInHand(
                0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_LANDS
        );
        player.choose(Outcome.PutCardInPlay, target, source, game);
        return player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
    }
}
