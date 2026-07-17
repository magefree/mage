package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.ShrinesYouControlCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoShintaiOfAncientWars extends CardImpl {

    public GoShintaiOfAncientWars(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // At the beginning of your end step, you may pay {1}. When you do, Go-Shintai of Ancient Wars deals X damage to target player or planeswalker, where X is the number of Shrines you control.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(ShrinesYouControlCount.WHERE_X), false, "{this} deals X damage " +
                "to target player or planeswalker, where X is the number of Shrines you control"
        );
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new DoWhenCostPaid(ability, new GenericManaCost(1), "Pay {1}?")
        ).addHint(ShrinesYouControlCount.getHint()));
    }

    private GoShintaiOfAncientWars(final GoShintaiOfAncientWars card) {
        super(card);
    }

    @Override
    public GoShintaiOfAncientWars copy() {
        return new GoShintaiOfAncientWars(this);
    }
}
