
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCostCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author FenrisulfrX
 */
public final class SunscapeBattlemage extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");
    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public SunscapeBattlemage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {1}{G} and/or {2}{U}
        KickerAbility kickerAbility = new KickerAbility("{1}{G}");
        kickerAbility.addKickerCost("{2}{U}");
        this.addAbility(kickerAbility);

        // When {this} enters the battlefield, if it was kicked with its {1}{G} kicker, destroy target creature with flying.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new KickedCostCondition("{1}{G}"),
                "When {this} enters the battlefield, if it was kicked with its {1}{G} kicker, destroy target creature with flying."));

        // When {this} enters the battlefield, if it was kicked with its {2}{U} kicker, draw two cards.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldTriggeredAbility(
                new DrawCardSourceControllerEffect(2)), new KickedCostCondition("{2}{U}"),
                "When {this} enters the battlefield, if it was kicked with its {2}{U} kicker, draw two cards."));
    }

    private SunscapeBattlemage(final SunscapeBattlemage card) {
        super(card);
    }

    @Override
    public SunscapeBattlemage copy() {
        return new SunscapeBattlemage(this);
    }
}
