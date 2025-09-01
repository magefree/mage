package mage.cards.h;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.HistoricPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HaviTheAllFather extends CardImpl {

    private static final FilterCard filterHistoric = new FilterCard("historic cards");
    private static final FilterCard filter = new FilterCreatureCard("legendary creature card with lesser mana value from your graveyard");

    static {
        filterHistoric.add(HistoricPredicate.instance);
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter.add(HaviTheAllFatherPredicate.instance);
    }

    private static final Condition condition = new CardsInControllerGraveyardCondition(4, filterHistoric);
    private static final Hint hint = new ValueHint(
            "Historic cards in your graveyard", new CardsInControllerGraveyardCount(filterHistoric)
    );

    public HaviTheAllFather(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Havi, the All-Father has indestructible as long as there are four or more historic cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance()), condition,
                "{this} has indestructible as long as there are four or more historic cards in your graveyard"
        )).addHint(hint));

        // Sage Project -- Whenever Havi or another legendary creature you control dies, return target legendary creature card with lesser mana value from your graveyard to the battlefield tapped.
        Ability ability = new DiesThisOrAnotherTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(true),
                false, StaticFilters.FILTER_CONTROLLED_CREATURE_LEGENDARY
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability.withFlavorWord("Sage Project"));
    }

    private HaviTheAllFather(final HaviTheAllFather card) {
        super(card);
    }

    @Override
    public HaviTheAllFather copy() {
        return new HaviTheAllFather(this);
    }
}

enum HaviTheAllFatherPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return input
                .getObject()
                .getManaValue()
                < CardUtil
                .getEffectValueFromAbility(
                        input.getSource(), "creatureDied", Permanent.class
                )
                .map(MageObject::getManaValue)
                .orElse(0);
    }
}
