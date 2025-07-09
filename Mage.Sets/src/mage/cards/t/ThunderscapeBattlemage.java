package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.KickedCostCondition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author FenrisulfrX
 */
public final class ThunderscapeBattlemage extends CardImpl {

    private static final Condition condition = new KickedCostCondition("{1}{B}");
    private static final Condition condition2 = new KickedCostCondition("{G}");

    public ThunderscapeBattlemage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {1}{B} and/or {G}
        KickerAbility kickerAbility = new KickerAbility("{1}{B}");
        kickerAbility.addKickerCost("{G}");
        this.addAbility(kickerAbility);

        // When {this} enters, if it was kicked with its {1}{B} kicker, target player discards two cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(2)).withInterveningIf(condition);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // When {this} enters, if it was kicked with its {G} kicker, destroy target enchantment.
        ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect()).withInterveningIf(condition2);
        ability.addTarget(new TargetEnchantmentPermanent());
        this.addAbility(ability);
    }

    private ThunderscapeBattlemage(final ThunderscapeBattlemage card) {
        super(card);
    }

    @Override
    public ThunderscapeBattlemage copy() {
        return new ThunderscapeBattlemage(this);
    }
}
