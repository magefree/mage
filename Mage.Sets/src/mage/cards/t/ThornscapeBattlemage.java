package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.KickedCostCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author FenrisulfrX
 */
public final class ThornscapeBattlemage extends CardImpl {

    private static final Condition condition = new KickedCostCondition("{R}");
    private static final Condition condition2 = new KickedCostCondition("{W}");

    public ThornscapeBattlemage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {R} and/or {W}
        KickerAbility kickerAbility = new KickerAbility("{R}");
        kickerAbility.addKickerCost("{W}");
        this.addAbility(kickerAbility);

        // When {this} enters, if it was kicked with its {R} kicker, it deals 2 damage to any target.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DamageTargetEffect(2, "it")
        ).withInterveningIf(condition);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // When {this} enters, if it was kicked with its {W} kicker, destroy target artifact.
        ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect()).withInterveningIf(condition2);
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    private ThornscapeBattlemage(final ThornscapeBattlemage card) {
        super(card);
    }

    @Override
    public ThornscapeBattlemage copy() {
        return new ThornscapeBattlemage(this);
    }
}
