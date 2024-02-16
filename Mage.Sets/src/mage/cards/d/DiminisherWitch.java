package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.BargainedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.abilities.hint.common.BargainCostWasPaidHint;
import mage.abilities.keyword.BargainAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.RoleType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DiminisherWitch extends CardImpl {

    public DiminisherWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Bargain
        this.addAbility(new BargainAbility());

        // When Diminisher Witch enters the battlefield, if it was bargained, create a Cursed Role token attached to target creature an opponent controls.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new CreateRoleAttachedTargetEffect(RoleType.CURSED)),
                BargainedCondition.instance, "When {this} enters the battlefield, if it was bargained, " +
                "create a Cursed Role token attached to target creature an opponent controls."
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability.addHint(BargainCostWasPaidHint.instance));
    }

    private DiminisherWitch(final DiminisherWitch card) {
        super(card);
    }

    @Override
    public DiminisherWitch copy() {
        return new DiminisherWitch(this);
    }
}
