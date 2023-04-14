package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

public class TheGreatSynthesis extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Phyrexian creatures");

    static {
        filter.add(Predicates.not(SubType.PHYREXIAN.getPredicate()));
    }

    public TheGreatSynthesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "");
        this.addSubType(SubType.SAGA);
        this.color.setBlue(true);
        this.nightCard = true;

        //(As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this, false);

        //I — Draw cards equal to the number of cards in your hand. You have no maximum hand size for as long as you
        //control The Great Synthesis.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I,
                new DrawCardSourceControllerEffect(CardsInControllerHandCount.instance)
                        .setText("draw cards equal to the number of cards in your hand"),
                new MaximumHandSizeControllerEffect(Integer.MAX_VALUE, Duration.WhileOnBattlefield,
                        MaximumHandSizeControllerEffect.HandSizeModification.SET)
                        .setText("you have no maximum hand size for as long as you control {this}"));

        //II — Return all non-Phyrexian creatures to their owners' hands.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new ReturnToHandFromBattlefieldAllEffect(filter));

        //III — You may cast any number of spells from your hand without paying their mana cost. Exile The Great
        //Synthesis, then return it to the battlefield <i>(front face up)</i>.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new TheGreatSynthesisCastEffect(),
                new TheGreatSynthesisExileReturnEffect());

        this.addAbility(sagaAbility);
    }

    private TheGreatSynthesis(final TheGreatSynthesis card) {
        super(card);
    }

    @Override
    public TheGreatSynthesis copy() {
        return new TheGreatSynthesis(this);
    }
}

class TheGreatSynthesisCastEffect extends OneShotEffect {
    public TheGreatSynthesisCastEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "you may cast any number of spells from your hand without paying their mana costs";
    }

    public TheGreatSynthesisCastEffect(final TheGreatSynthesisCastEffect effect) {
        super(effect);
    }

    @Override
    public TheGreatSynthesisCastEffect copy() {
        return new TheGreatSynthesisCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = controller.getHand();
        CardUtil.castMultipleWithAttributeForFree(controller, source, game, cards, StaticFilters.FILTER_CARD);
        return true;
    }
}

class TheGreatSynthesisExileReturnEffect extends OneShotEffect {
    public TheGreatSynthesisExileReturnEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "exile {this}, then return it to the battlefield <i>(front face up)</i>";
    }

    private TheGreatSynthesisExileReturnEffect(final TheGreatSynthesisExileReturnEffect effect) {
        super(effect);
    }

    @Override
    public TheGreatSynthesisExileReturnEffect copy() {
        return new TheGreatSynthesisExileReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getState().getZone(source.getSourceId()) != Zone.BATTLEFIELD) {
            return false;
        }
        Card card = game.getCard(source.getSourceId());
        if (card == null) {
            return false;
        }

        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        if (!player.moveCards(card, Zone.EXILED, source, game)) {
            return false;
        }
        return player.moveCards(card, Zone.BATTLEFIELD, source, game);
    }

}
