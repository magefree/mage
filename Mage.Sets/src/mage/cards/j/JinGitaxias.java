package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.ExileSourceAndReturnFaceUpEffect;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

public class JinGitaxias extends TransformingDoubleFacedCard {

    private static final FilterSpell filter = new FilterSpell("a noncreature spell with mana value 3 or greater");
    private static final FilterCreaturePermanent filterNonPhyrexian = new FilterCreaturePermanent("non-Phyrexian creatures");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 2));
        filterNonPhyrexian.add(Predicates.not(SubType.PHYREXIAN.getPredicate()));
    }

    public JinGitaxias(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PHYREXIAN, SubType.PRAETOR}, "{3}{U}{U}",
                "The Great Synthesis",
                new SuperType[]{}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "U");

        // Jin-Gitaxias
        this.getLeftHalfCard().setPT(5, 5);

        // Ward {2}
        this.getLeftHalfCard().addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // Whenever you cast a noncreature spell with mana value 3 or greater, draw a card.
        this.getLeftHalfCard().addAbility(new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter, false
        ));

        // {3}{U}: Exile Jin-Gitaxias, then return it to the battlefield transformed under its owner’s control. Activate only as a sorcery and only if you have seven or more cards in hand.
        this.getLeftHalfCard().addAbility(new ActivateIfConditionActivatedAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED),
                new ManaCostsImpl<>("{3}{U}"),
                new CardsInHandCondition(ComparisonType.MORE_THAN, 6)
        ).setTiming(TimingRule.SORCERY));

        // The Great Synthesis

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getRightHalfCard());

        // I — Draw cards equal to the number of cards in your hand. You have no maximum hand size for as long as you control The Great Synthesis.
        sagaAbility.addChapterEffect(this.getRightHalfCard(), SagaChapter.CHAPTER_I,
                new DrawCardSourceControllerEffect(CardsInControllerHandCount.ANY)
                        .setText("draw cards equal to the number of cards in your hand"),
                new MaximumHandSizeControllerEffect(Integer.MAX_VALUE, Duration.WhileOnBattlefield,
                        MaximumHandSizeControllerEffect.HandSizeModification.SET)
                        .setText("you have no maximum hand size for as long as you control {this}"));

        // II — Return all non-Phyrexian creatures to their owners' hands.
        sagaAbility.addChapterEffect(this.getRightHalfCard(), SagaChapter.CHAPTER_II, new ReturnToHandFromBattlefieldAllEffect(filterNonPhyrexian));

        // III — You may cast any number of spells from your hand without paying their mana cost. Exile The Great Synthesis, then return it to the battlefield (front face up).
        sagaAbility.addChapterEffect(
                this.getRightHalfCard(), SagaChapter.CHAPTER_III,
                new TheGreatSynthesisCastEffect(),
                new ExileSourceAndReturnFaceUpEffect()
        );
        this.getRightHalfCard().addAbility(sagaAbility);
    }

    private JinGitaxias(final JinGitaxias card) {
        super(card);
    }

    @Override
    public JinGitaxias copy() {
        return new JinGitaxias(this);
    }
}

class TheGreatSynthesisCastEffect extends OneShotEffect {
    public TheGreatSynthesisCastEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "you may cast any number of spells from your hand without paying their mana costs";
    }

    private TheGreatSynthesisCastEffect(final TheGreatSynthesisCastEffect effect) {
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
