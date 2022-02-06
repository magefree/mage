package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.util.RandomUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InventiveIteration extends CardImpl {

    public InventiveIteration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.l.LivingBreakthrough.class;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Return up to one target creature or planeswalker to its owner's hand.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
                new ReturnToHandTargetEffect(), new TargetCreatureOrPlaneswalker(0, 1)
        );

        // II — Return an artifact card from your graveyard to your hand. If you can't, draw a card.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new InventiveIterationEffect());

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.addAbility(sagaAbility);
    }

    private InventiveIteration(final InventiveIteration card) {
        super(card);
    }

    @Override
    public InventiveIteration copy() {
        return new InventiveIteration(this);
    }
}

class InventiveIterationEffect extends OneShotEffect {

    InventiveIterationEffect() {
        super(Outcome.Benefit);
        staticText = "return an artifact card from your graveyard to your hand. If you can't, draw a card";
    }

    private InventiveIterationEffect(final InventiveIterationEffect effect) {
        super(effect);
    }

    @Override
    public InventiveIterationEffect copy() {
        return new InventiveIterationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card;
        switch (player.getGraveyard().count(StaticFilters.FILTER_CARD_ARTIFACT, game)) {
            case 0:
                player.drawCards(1, source, game);
                return true;
            case 1:
                card = RandomUtil.randomFromCollection(
                        player.getGraveyard().getCards(StaticFilters.FILTER_CARD_ARTIFACT, game)
                );
                break;
            default:
                TargetCard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT);
                target.setNotTarget(true);
                player.choose(outcome, player.getGraveyard(), target, game);
                card = game.getCard(target.getFirstTarget());
        }
        player.moveCards(card, Zone.HAND, source, game);
        return true;
    }
}
