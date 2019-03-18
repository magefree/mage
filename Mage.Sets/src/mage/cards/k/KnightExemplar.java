

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class KnightExemplar extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Knight creatures");

    static {
        filter.add(new SubtypePredicate(SubType.KNIGHT));
    }

    public KnightExemplar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Other Knight creatures you control get +1/+1 and are indestructible.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));
        FilterCreaturePermanent indestructibleFilter = filter.copy();
        indestructibleFilter.add(AnotherPredicate.instance);
        indestructibleFilter.add(new ControllerPredicate(TargetController.YOU));
        indestructibleFilter.setMessage("Other Knight creatures you control");
        Effect effect = new GainAbilityAllEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, indestructibleFilter, false);
        effect.setText("Other Knight creatures you control are indestructible");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

    }

    public KnightExemplar(final KnightExemplar card) {
        super(card);
    }

    @Override
    public KnightExemplar copy() {
        return new KnightExemplar(this);
    }

}
