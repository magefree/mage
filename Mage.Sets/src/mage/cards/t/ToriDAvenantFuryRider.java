package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;

/**
 *
 * @author weirddan455
 */
public final class ToriDAvenantFuryRider extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("all other attacking creatures");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("red attacking creatures");
    private static final FilterCreaturePermanent filter3 = new FilterCreaturePermanent("each other white attacking creature");

    static {
        filter.add(AttackingPredicate.instance);
        filter2.add(new ColorPredicate(ObjectColor.RED));
        filter2.add(AttackingPredicate.instance);
        filter3.add(new ColorPredicate(ObjectColor.WHITE));
        filter3.add(AttackingPredicate.instance);
    }

    public ToriDAvenantFuryRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Tori D'Avenant, Fury Rider attacks, all other attacking creatures you control get +1/+1 until end of turn.
        // Other red attacking creatures you control gain trample until end of turn.
        // Untap each other white attacking creature you control.
        Ability ability = new AttacksTriggeredAbility(new BoostControlledEffect(1, 1, Duration.EndOfTurn, filter, true));
        ability.addEffect(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, filter2, true));
        ability.addEffect(new UntapAllControllerEffect(filter3, "Untap each other white attacking creature you control", false));
        this.addAbility(ability);
    }

    private ToriDAvenantFuryRider(final ToriDAvenantFuryRider card) {
        super(card);
    }

    @Override
    public ToriDAvenantFuryRider copy() {
        return new ToriDAvenantFuryRider(this);
    }
}
