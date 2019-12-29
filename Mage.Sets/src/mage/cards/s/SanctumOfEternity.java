package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.other.OwnerPredicate;
import mage.filter.predicate.permanent.CommanderPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SanctumOfEternity extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("commander you own");

    static {
        filter.add(CommanderPredicate.instance);
        filter.add(new OwnerPredicate(TargetController.YOU));
    }

    public SanctumOfEternity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {2}, {T}: Return target commander you own from the battlefield to your hand. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new ReturnToHandTargetEffect()
                .setText("return target commander you own from the battlefield to your hand"),
                new GenericManaCost(2), MyTurnCondition.instance
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SanctumOfEternity(final SanctumOfEternity card) {
        super(card);
    }

    @Override
    public SanctumOfEternity copy() {
        return new SanctumOfEternity(this);
    }
}
