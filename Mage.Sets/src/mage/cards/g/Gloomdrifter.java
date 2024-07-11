package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class Gloomdrifter extends CardImpl {

    public Gloomdrifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Threshold - As long as seven or more cards are in your graveyard, Gloomdrifter has "When Gloomdrifter enters the battlefield, nonblack creatures get -2/-2 until end of turn."
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new EntersBattlefieldTriggeredAbility(
                        new BoostAllEffect(
                                -2, -2, Duration.EndOfTurn,
                                StaticFilters.FILTER_PERMANENT_CREATURES_NON_BLACK, false
                        ))), ThresholdCondition.instance, "as long as seven or more cards are in your graveyard, " +
                "{this} has \"When {this} enters the battlefield, nonblack creatures get -2/-2 until end of turn.\""
        )).setAbilityWord(AbilityWord.THRESHOLD));
    }

    private Gloomdrifter(final Gloomdrifter card) {
        super(card);
    }

    @Override
    public Gloomdrifter copy() {
        return new Gloomdrifter(this);
    }
}
