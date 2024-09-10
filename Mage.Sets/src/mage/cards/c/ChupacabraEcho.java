package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ChupacabraEcho extends CardImpl {

    private static final DynamicValue xValue =
            new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_PERMANENT, -1);

    public ChupacabraEcho(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.HORROR);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Fathomless descent -- When Chupacabra Echo enters the battlefield, target creature an opponent controls gets -X/-X until end of turn, where X is the number of permanent cards in your graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn)
                        .setText("target creature an opponent controls gets -X/-X until end of turn, " +
                                "where X is the number of permanent cards in your graveyard")
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability.setAbilityWord(AbilityWord.FATHOMLESS_DESCENT).addHint(DescendCondition.getHint()));
    }

    private ChupacabraEcho(final ChupacabraEcho card) {
        super(card);
    }

    @Override
    public ChupacabraEcho copy() {
        return new ChupacabraEcho(this);
    }
}
