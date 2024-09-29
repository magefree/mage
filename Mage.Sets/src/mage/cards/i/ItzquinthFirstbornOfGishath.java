package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ItzquinthFirstbornOfGishath extends CardImpl {

    private static final FilterControlledPermanent filter1 = new FilterControlledCreaturePermanent(SubType.DINOSAUR, "Dinosaur you control");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("another target creature");

    static {
        filter2.add(new AnotherTargetPredicate(2));
    }

    public ItzquinthFirstbornOfGishath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Itzquinth enters the battlefield, you may pay {2}. When you do, target Dinosaur you control deals damage equal to its power to another target creature.
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(new DamageWithPowerFromOneToAnotherTargetEffect(), false);
        reflexive.addTarget(new TargetControlledPermanent(filter1).setTargetTag(1)); // may not be a creature. Don't ask me why.
        reflexive.addTarget(new TargetCreaturePermanent(filter2).setTargetTag(2));
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoWhenCostPaid(reflexive, new GenericManaCost(2), "Pay {2}?")
        ));
    }

    private ItzquinthFirstbornOfGishath(final ItzquinthFirstbornOfGishath card) {
        super(card);
    }

    @Override
    public ItzquinthFirstbornOfGishath copy() {
        return new ItzquinthFirstbornOfGishath(this);
    }
}
