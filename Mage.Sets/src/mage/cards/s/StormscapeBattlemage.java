package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.KickedCostCondition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class StormscapeBattlemage extends CardImpl {

    private static final Condition condition = new KickedCostCondition("{W}");
    private static final Condition condition2 = new KickedCostCondition("{2}{B}");

    public StormscapeBattlemage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.METATHRAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {W} and/or {2}{B}
        KickerAbility kickerAbility = new KickerAbility("{W}");
        kickerAbility.addKickerCost("{2}{B}");
        this.addAbility(kickerAbility);

        // When Stormscape Battlemage enters the battlefield, if it was kicked with its {W} kicker, you gain 3 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3)).withInterveningIf(condition));

        // When Stormscape Battlemage enters the battlefield, if it was kicked with its {2}{B} kicker, destroy target nonblack creature. That creature can't be regenerated.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(
                "destroy target nonblack creature. That creature can't be regenerated", true
        )).withInterveningIf(condition2);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
        this.addAbility(ability);
    }

    private StormscapeBattlemage(final StormscapeBattlemage card) {
        super(card);
    }

    @Override
    public StormscapeBattlemage copy() {
        return new StormscapeBattlemage(this);
    }
}
