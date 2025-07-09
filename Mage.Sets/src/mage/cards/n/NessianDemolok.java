package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.TributeNotPaidCondition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.TributeAbility;
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
public final class NessianDemolok extends CardImpl {

    public NessianDemolok(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Tribute 3
        this.addAbility(new TributeAbility(3));

        // When Nessian Demolok enters the battlefield, if tribute wasn't paid, destroy target noncreature permanent.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect())
                .withInterveningIf(TributeNotPaidCondition.instance);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_NON_CREATURE));
        this.addAbility(ability);
    }

    private NessianDemolok(final NessianDemolok card) {
        super(card);
    }

    @Override
    public NessianDemolok copy() {
        return new NessianDemolok(this);
    }
}
