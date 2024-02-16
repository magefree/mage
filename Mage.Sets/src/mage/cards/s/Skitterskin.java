
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author fireshoes
 */
public final class Skitterskin extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("you control another colorless creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(ColorlessPredicate.instance);
    }

    public Skitterskin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Skitterskin can't block.
        this.addAbility(new CantBlockAbility());

        // {1}{B}: Regenerate Skitterskin. Activate this ability only if you control another colorless creature.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD,
                new RegenerateSourceEffect(),
                new ManaCostsImpl<>("{1}{B}"),
                new PermanentsOnTheBattlefieldCondition(filter));
        this.addAbility(ability);
    }

    private Skitterskin(final Skitterskin card) {
        super(card);
    }

    @Override
    public Skitterskin copy() {
        return new Skitterskin(this);
    }
}
