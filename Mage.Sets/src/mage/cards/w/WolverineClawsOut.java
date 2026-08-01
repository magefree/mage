package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.DamageAsThoughNotBlockedAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.dynamicvalue.common.TargetPermanentPowerCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class WolverineClawsOut extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.MUTANT, "Mutant you control");

    public WolverineClawsOut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.BERSERKER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // You may have Wolverine assign his combat damage as though he weren't blocked.
        this.addAbility(DamageAsThoughNotBlockedAbility.getInstance());

        // Whenever a Mutant you control attacks, double its power until end of turn.
        Ability ability = new AttacksCreatureYouControlTriggeredAbility(
            new BoostTargetEffect(TargetPermanentPowerCount.instance, StaticValue.get(0)),
            false, filter, true
        );
        this.addAbility(ability);
    }

    private WolverineClawsOut(final WolverineClawsOut card) {
        super(card);
    }

    @Override
    public WolverineClawsOut copy() {
        return new WolverineClawsOut(this);
    }
}
