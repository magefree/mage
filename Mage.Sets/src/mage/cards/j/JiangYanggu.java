
package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.permanent.token.MowuToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class JiangYanggu extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature name Mowu");

    static {
        filter.add(new NamePredicate("Mowu"));
    }

    public JiangYanggu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.YANGGU);
        this.setStartingLoyalty(4);

        // +1: Target creature gets +2/+2 until end of turn.
        Ability ability = new LoyaltyAbility(new BoostTargetEffect(2, 2, Duration.EndOfTurn), 1);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // -1: If you don't control a creature named Mowu, create Mowu, a legendary 3/3 green Dog creature token.
        this.addAbility(new LoyaltyAbility(new ConditionalOneShotEffect(
                new CreateTokenEffect(new MowuToken()),
                new InvertCondition(new PermanentsOnTheBattlefieldCondition(filter)),
                "If you don't control a creature named Mowu, "
                + "create Mowu, a legendary 3/3 green Dog creature token."
        ), -1));

        // -5: Until end of turn, target creature gains trample and gets +X/+X, where X is the number of lands you control.
        DynamicValue controlledLands = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS);
        ability = new LoyaltyAbility(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(),
                Duration.EndOfTurn,
                "Until end of turn, target creature gains trample"
        ), -5);
        ability.addEffect(
                new BoostTargetEffect(controlledLands, controlledLands, Duration.EndOfTurn)
                        .setText("and gets +X/+X, where X is the number of lands you control")
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private JiangYanggu(final JiangYanggu card) {
        super(card);
    }

    @Override
    public JiangYanggu copy() {
        return new JiangYanggu(this);
    }
}
