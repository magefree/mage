package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnBeforeAttackersDeclaredCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class StoneCatapult extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("tapped nonblack creature");

    static {
        filter.add(TappedPredicate.TAPPED);
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public StoneCatapult(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {tap}: Destroy target tapped nonblack creature. Activate this ability only during your turn, before attackers are declared.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new DestroyTargetEffect(), new TapSourceCost(), MyTurnBeforeAttackersDeclaredCondition.instance
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private StoneCatapult(final StoneCatapult card) {
        super(card);
    }

    @Override
    public StoneCatapult copy() {
        return new StoneCatapult(this);
    }
}
