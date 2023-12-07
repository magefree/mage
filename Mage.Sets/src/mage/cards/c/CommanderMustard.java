package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CommanderMustard extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SOLDIER, "Soldiers");

    public CommanderMustard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Other Soldiers you control have vigilance, trample, and haste.
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        ).setText(", trample"));
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        ).setText(", and haste"));
        this.addAbility(ability);

        // {2}{R}{W}: Until end of turn, Soldiers you control gain "Whenever this creature attacks, it deals 1 damage to defending player."
        this.addAbility(new SimpleActivatedAbility(new GainAbilityControlledEffect(new AttacksTriggeredAbility(
                new DamageTargetEffect(1), false, "Whenever this creature attacks, " +
                "it deals 1 damage to defending player.", SetTargetPointer.PLAYER
        ), Duration.WhileOnBattlefield, filter, true), new ManaCostsImpl<>("{2}{R}{W}")));
    }

    private CommanderMustard(final CommanderMustard card) {
        super(card);
    }

    @Override
    public CommanderMustard copy() {
        return new CommanderMustard(this);
    }
}
