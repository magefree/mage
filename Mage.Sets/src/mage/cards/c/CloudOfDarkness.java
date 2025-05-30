package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CloudOfDarkness extends CardImpl {

    private static final DynamicValue xValue =
            new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_PERMANENT, -1);

    public CloudOfDarkness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Particle Beam -- When Cloud of Darkness enters, target creature an opponent controls gets -X/-X until end of turn, where X is the number of permanent cards in your graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(xValue, xValue)
                .setText("target creature an opponent controls gets -X/-X until end of turn, " +
                        "where X is the number of permanent cards in your graveyard"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability.withFlavorWord("Particle Beam").addHint(DescendCondition.getHint()));
    }

    private CloudOfDarkness(final CloudOfDarkness card) {
        super(card);
    }

    @Override
    public CloudOfDarkness copy() {
        return new CloudOfDarkness(this);
    }
}
