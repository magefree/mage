package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author varaghar
 */
public final class KingNarfisBetrayal extends CardImpl {

    public KingNarfisBetrayal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{B}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        //I — Each player mills four cards. You may exile up to one creature or planeswalker card from each graveyard.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new KingNarfisBetrayalFirstEffect());

        //II, III — Until end of turn, you may cast spells from among cards exiled with King Narfi's Betrayal, and you may spend mana as though it were mana of any color to cast those spells.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_III, new KingNarfisBetrayalSecondEffect());

        this.addAbility(sagaAbility);
    }

    private KingNarfisBetrayal(final KingNarfisBetrayal card) {
        super(card);
    }

    @Override
    public KingNarfisBetrayal copy() {
        return new KingNarfisBetrayal(this);
    }
}

class KingNarfisBetrayalFirstEffect extends OneShotEffect {

    private static final Effect millEffect = new MillCardsEachPlayerEffect(4, TargetController.EACH_PLAYER);
    private static final FilterCard filter = new FilterCard("creature or planeswalker card from each graveyard");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    KingNarfisBetrayalFirstEffect() {
        super(Outcome.Benefit);
        staticText = "Each player mills four cards. Then you may exile a creature or planeswalker " +
                "card from each graveyard";
    }

    private KingNarfisBetrayalFirstEffect(final KingNarfisBetrayalFirstEffect effect) {
        super(effect);
    }

    @Override
    public KingNarfisBetrayalFirstEffect copy() {
        return new KingNarfisBetrayalFirstEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID controllerId = source.getControllerId();
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }
        millEffect.apply(game, source);

        //Controller graveyard
        if (controller.getGraveyard().count(filter, game) != 0) {
            if (controller.chooseUse(outcome, "Exile a creature or planeswalker card from your graveyard?", source, game)) {
                TargetCard target = new TargetCardInYourGraveyard(filter);
                target.setNotTarget(true);
                if (controller.chooseTarget(outcome, controller.getGraveyard(), target, source, game)) {
                    controller.moveCardsToExile(game.getCard(target.getFirstTarget()), source, game, true, CardUtil.getCardExileZoneId(game, source), CardUtil.createObjectRealtedWindowTitle(source, game, null));
                }
            }
        }

        //Each opponent's graveyard
        for (UUID opponentId : game.getOpponents(controllerId)) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }

            if (opponent.getGraveyard().count(filter, game) != 0) {
                if (controller.chooseUse(outcome, "Exile a creature or planeswalker card from " + opponent.getName() + "'s graveyard?", source, game)) {
                    TargetCard target = new TargetCardInOpponentsGraveyard(1, 1, filter, true);
                    target.setNotTarget(true);
                    if (controller.chooseTarget(outcome, opponent.getGraveyard(), target, source, game)) {
                        controller.moveCardsToExile(game.getCard(target.getFirstTarget()), source, game, true, CardUtil.getCardExileZoneId(game, source), CardUtil.createObjectRealtedWindowTitle(source, game, null));
                    }
                }
            }
        }

        return true;
    }
}

class KingNarfisBetrayalSecondEffect extends OneShotEffect {


    public KingNarfisBetrayalSecondEffect() {
        super(Outcome.Benefit);
        this.staticText = "Until end of turn, you may cast spells from among cards exiled with King Narfi's Betrayal," +
                " and you may spend mana as though it were mana of any color to cast those spells";
    }

    public KingNarfisBetrayalSecondEffect(final KingNarfisBetrayalSecondEffect effect) {
        super(effect);
    }

    @Override
    public KingNarfisBetrayalSecondEffect copy() {
        return new KingNarfisBetrayalSecondEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ExileZone zone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
        if (zone != null) {
            for (Card card : zone.getCards(game)) {
                CardUtil.makeCardPlayable(game, source, card, Duration.EndOfTurn, true);
            }
        }

        return false;
    }

}
