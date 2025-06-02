package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ControllerSpeedCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheSpeedDemon extends CardImpl {

    public TheSpeedDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // At the beginning of your end step, you draw X cards and lose X life, where X is your speed.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new DrawCardSourceControllerEffect(ControllerSpeedCount.instance).setText("you draw X cards")
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(ControllerSpeedCount.instance)
                .setText("and lose X life, where X is your speed"));
        this.addAbility(ability);
    }

    private TheSpeedDemon(final TheSpeedDemon card) {
        super(card);
    }

    @Override
    public TheSpeedDemon copy() {
        return new TheSpeedDemon(this);
    }
}
