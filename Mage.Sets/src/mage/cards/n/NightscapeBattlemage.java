package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.KickedCostCondition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class NightscapeBattlemage extends CardImpl {

    private static final Condition condition = new KickedCostCondition("{2}{U}");
    private static final Condition condition2 = new KickedCostCondition("{2}{R}");

    public NightscapeBattlemage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {2}{U} and/or {2}{R}
        KickerAbility kickerAbility = new KickerAbility("{2}{U}");
        kickerAbility.addKickerCost("{2}{R}");
        this.addAbility(kickerAbility);

        // When Nightscape Battlemage enters the battlefield, if it was kicked with its {2}{U} kicker, return up to two target nonblack creatures to their owners' hands.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), false).withInterveningIf(condition);
        ability.addTarget(new TargetPermanent(0, 2, StaticFilters.FILTER_PERMANENT_CREATURES_NON_BLACK));
        this.addAbility(ability);

        // When Nightscape Battlemage enters the battlefield, if it was kicked with its {2}{R} kicker, destroy target land.
        ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), false).withInterveningIf(condition2);
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    private NightscapeBattlemage(final NightscapeBattlemage card) {
        super(card);
    }

    @Override
    public NightscapeBattlemage copy() {
        return new NightscapeBattlemage(this);
    }
}
