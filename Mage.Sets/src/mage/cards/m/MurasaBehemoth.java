package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
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
public final class MurasaBehemoth extends CardImpl {

    private static final Condition condition = new CardsInControllerGraveyardCondition(1, StaticFilters.FILTER_CARD_LAND);

    public MurasaBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Murasa Behemoth gets +3/+3 as long as there is a land card in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(3, 3, Duration.WhileOnBattlefield),
                condition, "{this} gets +3/+3 as long as there is a land card in your graveyard"
        )));
    }

    private MurasaBehemoth(final MurasaBehemoth card) {
        super(card);
    }

    @Override
    public MurasaBehemoth copy() {
        return new MurasaBehemoth(this);
    }
}
