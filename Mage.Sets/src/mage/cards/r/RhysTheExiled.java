package mage.cards.r;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 * @author Loki
 */
public final class RhysTheExiled extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.ELF, "Elf");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent(SubType.ELF, "Elf you control");
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter2, 1);

    public RhysTheExiled(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Rhys the Exiled attacks, you gain 1 life for each Elf you control.
        this.addAbility(new AttacksTriggeredAbility(new GainLifeEffect(xValue)
                .setText("you gain 1 life for each Elf you control"), false));

        // {B}, Sacrifice an Elf: Regenerate Rhys the Exiled.
        Ability ability = new SimpleActivatedAbility(new RegenerateSourceEffect(), new ManaCostsImpl<>("{B}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    private RhysTheExiled(final RhysTheExiled card) {
        super(card);
    }

    @Override
    public RhysTheExiled copy() {
        return new RhysTheExiled(this);
    }
}
