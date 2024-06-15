package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TheCreationOfAvacyn extends CardImpl {

    public TheCreationOfAvacyn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Search your library for a card, exile it face down, then shuffle.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new TheCreationOfAvacynOneEffect());

        // II -- Turn the exiled card face up. If it's a creature card, you lose life equal to its mana value.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new TheCreationOfAvacynTwoEffect());

        // III -- You may put the exiled card onto the battlefield if it's a creature card. If you don't put it onto the battlefield, put it into its owner's hand.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new TheCreationOfAvacynThreeEffect());

        this.addAbility(sagaAbility);
    }

    private TheCreationOfAvacyn(final TheCreationOfAvacyn card) {
        super(card);
    }

    @Override
    public TheCreationOfAvacyn copy() {
        return new TheCreationOfAvacyn(this);
    }
}

class TheCreationOfAvacynOneEffect extends OneShotEffect {

    TheCreationOfAvacynOneEffect() {
        super(Outcome.Benefit);
        staticText = "Search your library for a card, exile it face down, then shuffle";
    }

    private TheCreationOfAvacynOneEffect(final TheCreationOfAvacynOneEffect effect) {
        super(effect);
    }

    @Override
    public TheCreationOfAvacynOneEffect copy() {
        return new TheCreationOfAvacynOneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        // Search your library for a card
        TargetCardInLibrary target = new TargetCardInLibrary();
        if (controller.searchLibrary(target, source, game)) {
            Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
            if (card != null) {
                // exile it face down
                card.setFaceDown(true, game);
                UUID exileId = CardUtil.getExileZoneId(game, source, 1);
                MageObject sourceObject = source.getSourceObject(game);
                String exileName = sourceObject != null ? sourceObject.getName() : "";
                controller.moveCardsToExile(card, source, game, false, exileId, exileName);
                card.setFaceDown(true, game);

                // then shuffle
                controller.shuffleLibrary(source, game);

            }
        }
        return true;
    }
}

class TheCreationOfAvacynTwoEffect extends OneShotEffect {

    TheCreationOfAvacynTwoEffect() {
        super(Outcome.Neutral);
        staticText = "Turn the exiled card face up. If it's a creature card, you lose life equal to its mana value";
    }

    private TheCreationOfAvacynTwoEffect(final TheCreationOfAvacynTwoEffect effect) {
        super(effect);
    }

    @Override
    public TheCreationOfAvacynTwoEffect copy() {
        return new TheCreationOfAvacynTwoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // From Release Notes ruling:
        // In the unusual case where two or more cards are exiled face down with The Creation of Avacyn's first chapter
        // ability (likely because the triggered ability was copied or the ability triggered a second time), the second
        // chapter ability will turn all of the exiled cards face up. If at least one of them is a creature card, you'll
        // lose life equal to the combined mana value of all the exiled cards.
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        if (exileZone == null || exileZone.isEmpty()) {
            return false;
        }
        boolean creatureCard = false;
        int mv = 0;
        for (Card card : exileZone.getCards(game)) {
            card.setFaceDown(false, game);
            creatureCard |= card.isCreature(game);
            mv += card.getManaValue();
        }
        if (creatureCard) {
            new LoseLifeSourceControllerEffect(mv)
                    .apply(game, source);
        }
        return true;
    }
}

class TheCreationOfAvacynThreeEffect extends OneShotEffect {

    TheCreationOfAvacynThreeEffect() {
        super(Outcome.Neutral);
        staticText = "You may put the exiled card onto the battlefield if it's a creature card. "
                + "If you don't put it onto the battlefield, put it into its owner's hand.";
    }

    private TheCreationOfAvacynThreeEffect(final TheCreationOfAvacynThreeEffect effect) {
        super(effect);
    }

    @Override
    public TheCreationOfAvacynThreeEffect copy() {
        return new TheCreationOfAvacynThreeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // From Release Notes Ruling:
        // In the unusual case where two or more cards are exiled face down with The Creation of Avacyn's first ability
        // when the third chapter ability resolves, if at least one of the exiled cards is a creature card, you may choose
        // to put all or none of the exiled cards that are permanent cards onto the battlefield. Regardless of what you
        // choose, any remaining exiled cards will be put into their owners' hands.
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        Player controller = game.getPlayer(source.getControllerId());
        if (exileZone == null || exileZone.isEmpty()) {
            return false;
        }
        boolean creatureCard = false;
        for (Card card : exileZone.getCards(game)) {
            creatureCard |= card.isCreature(game);
        }
        if (creatureCard) {
            if (controller.chooseUse(Outcome.PutCreatureInPlay, "Put the permanent in play?", source, game)) {
                controller.moveCards(exileZone, Zone.BATTLEFIELD, source, game);
                game.processAction();
            }
        }
        controller.moveCards(exileZone, Zone.HAND, source, game);
        return true;
    }

}
