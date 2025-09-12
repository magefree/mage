package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.KickedCostCondition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author FenrisulfrX
 */
public final class SunscapeBattlemage extends CardImpl {

    private static final Condition condition = new KickedCostCondition("{1}{G}");
    private static final Condition condition2 = new KickedCostCondition("{2}{U}");

    public SunscapeBattlemage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {1}{G} and/or {2}{U}
        KickerAbility kickerAbility = new KickerAbility("{1}{G}");
        kickerAbility.addKickerCost("{2}{U}");
        this.addAbility(kickerAbility);

        // When {this} enters, if it was kicked with its {1}{G} kicker, destroy target creature with flying.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect()).withInterveningIf(condition);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
        this.addAbility(ability);

        // When {this} enters, if it was kicked with its {2}{U} kicker, draw two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(2)).withInterveningIf(condition2));
    }

    private SunscapeBattlemage(final SunscapeBattlemage card) {
        super(card);
    }

    @Override
    public SunscapeBattlemage copy() {
        return new SunscapeBattlemage(this);
    }
}
