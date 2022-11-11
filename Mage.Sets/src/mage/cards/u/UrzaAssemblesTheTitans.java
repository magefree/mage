package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPlaneswalkerCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class UrzaAssemblesTheTitans extends CardImpl {

    private static final FilterPlaneswalkerCard filter = new FilterPlaneswalkerCard("a planeswalker card with mana value 6 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 7));
    }

    public UrzaAssemblesTheTitans(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}");

        this.subtype.add(SubType.SAGA);

        // Read ahead
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_III, true);

        // I--Scry 4, then you may reveal the top card of your library. If a planeswalker card is revealed this way, put it into your hand.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new UrzaAssemblesTheTitansScryEffect());

        // II--You may put a planeswalker card with mana value 6 or less from your hand onto the battlefield.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new PutCardFromHandOntoBattlefieldEffect(filter));

        // III--You may activate the loyalty abilities of planeswalkers you control twice this turn rather than only once.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new UrzaAssemblesTheTitansLoyaltyEffect());
        this.addAbility(sagaAbility);
    }

    private UrzaAssemblesTheTitans(final UrzaAssemblesTheTitans card) {
        super(card);
    }

    @Override
    public UrzaAssemblesTheTitans copy() {
        return new UrzaAssemblesTheTitans(this);
    }
}

class UrzaAssemblesTheTitansScryEffect extends OneShotEffect {

    public UrzaAssemblesTheTitansScryEffect() {
        super(Outcome.Benefit);
        this.staticText = "Scry 4, then you may reveal the top card of your library. If a planeswalker card is revealed this way, put it into your hand.";
    }

    private UrzaAssemblesTheTitansScryEffect(final UrzaAssemblesTheTitansScryEffect effect) {
        super(effect);
    }

    @Override
    public UrzaAssemblesTheTitansScryEffect copy() {
        return new UrzaAssemblesTheTitansScryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.scry(4, source, game);
        Card card = controller.getLibrary().getFromTop(game);
        if (card != null && controller.chooseUse(outcome, "Reveal the top card of your library?", source, game)) {
            controller.revealCards(source, new CardsImpl(card), game);
            if (card.isPlaneswalker(game)) {
                controller.moveCards(card, Zone.HAND, source, game);
            }
        }
        return true;
    }
}

class UrzaAssemblesTheTitansLoyaltyEffect extends ContinuousEffectImpl {

    public UrzaAssemblesTheTitansLoyaltyEffect() {
        super(Duration.EndOfTurn, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        this.staticText = "You may activate the loyalty abilities of planeswalkers you control twice this turn rather than only once.";
    }

    private UrzaAssemblesTheTitansLoyaltyEffect(final UrzaAssemblesTheTitansLoyaltyEffect effect) {
        super(effect);
    }

    @Override
    public UrzaAssemblesTheTitansLoyaltyEffect copy() {
        return new UrzaAssemblesTheTitansLoyaltyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER,
                source.getControllerId(), source, game
        )) {
            permanent.incrementLoyaltyActivationsAvailable(2);
        }
        return true;
    }
}
