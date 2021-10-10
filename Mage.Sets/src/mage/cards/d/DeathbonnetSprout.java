package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeathbonnetSprout extends CardImpl {

    private static final Condition condition
            = new CardsInControllerGraveyardCondition(3, StaticFilters.FILTER_CARD_CREATURE);
    private static final Hint hint = new ValueHint(
            "Creature cards in your graveyard",
            new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE)
    );

    public DeathbonnetSprout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.transformable = true;
        this.secondSideCardClazz = mage.cards.d.DeathbonnetHulk.class;

        // At the beginning of your upkeep, mill a card. Then if there are three or more creature cards in your graveyard, transform Deathbonnet Sprout.
        this.addAbility(new TransformAbility());
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(1));
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(true), condition,
                "Then if there are three or more creature cards in your graveyard, transform {this}"
        ));
        this.addAbility(ability.addHint(hint));
    }

    private DeathbonnetSprout(final DeathbonnetSprout card) {
        super(card);
    }

    @Override
    public DeathbonnetSprout copy() {
        return new DeathbonnetSprout(this);
    }
}
