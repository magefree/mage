package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LoneFox
 */
public final class Gloomdrifter extends CardImpl {

    public Gloomdrifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Threshold - As long as seven or more cards are in your graveyard, Gloomdrifter has "When Gloomdrifter enters the battlefield, nonblack creatures get -2/-2 until end of turn."
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(new EntersBattlefieldTriggeredAbility(
            new BoostAllEffect(-2, -2, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES_NON_BLACK, false))),
            new CardsInControllerGraveyardCondition(7),
            "As long as seven or more cards are in your graveyard, {this} has \"When {this} enters the battlefield, nonblack creatures get -2/-2 until end of turn.\""));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    private Gloomdrifter(final Gloomdrifter card) {
        super(card);
    }

    @Override
    public Gloomdrifter copy() {
        return new Gloomdrifter(this);
    }
}
