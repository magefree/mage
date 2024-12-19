package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MagmaticChanneler extends CardImpl {

    private static final Condition condition
            = new CardsInControllerGraveyardCondition(4, StaticFilters.FILTER_CARD_INSTANT_AND_SORCERY);
    private static final DynamicValue cardsCount
            = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY);
    private static final Hint hint
            = new ValueHint("Instant and sorcery cards in your graveyard", cardsCount);

    public MagmaticChanneler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // As long as there are four or more instant and/or sorcery cards in your graveyard, Magmatic Channeler gets +3/+1.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(3, 1, Duration.WhileOnBattlefield), condition,
                "as long as there are four or more instant and/or sorcery cards in your graveyard, {this} gets +3/+1"
        )).addHint(hint));

        // {T}, Discard a card: Exile the top two cards of your library, then choose one of them. You may play that card this turn.
        Ability ability = new SimpleActivatedAbility(new ExileTopXMayPlayUntilEffect(2, true, Duration.EndOfTurn), new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private MagmaticChanneler(final MagmaticChanneler card) {
        super(card);
    }

    @Override
    public MagmaticChanneler copy() {
        return new MagmaticChanneler(this);
    }
}
