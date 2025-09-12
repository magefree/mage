package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.TributeNotPaidCondition;
import mage.abilities.effects.common.FightTargetSourceEffect;
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
public final class NessianWildsRavager extends CardImpl {

    public NessianWildsRavager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.subtype.add(SubType.HYDRA);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Tribute 6
        this.addAbility(new TributeAbility(6));

        // When Nessian Wilds Ravager enters the battlefield, if tribute wasn't paid, you may have Nessian Wilds Ravager fight another target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new FightTargetSourceEffect().setText("have {this} fight another target creature"), true
        ).withInterveningIf(TributeNotPaidCondition.instance);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);
    }

    private NessianWildsRavager(final NessianWildsRavager card) {
        super(card);
    }

    @Override
    public NessianWildsRavager copy() {
        return new NessianWildsRavager(this);
    }
}
