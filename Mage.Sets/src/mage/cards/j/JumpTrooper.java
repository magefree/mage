package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.BecomesTargetAnyTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public final class JumpTrooper extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a Trooper creature you control");

    static {
        filter.add(SubType.TROOPER.getPredicate());
    }

    public JumpTrooper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.TROOPER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a Trooper creature you control becomes the target of a spell or ability an opponent controls, counter that spell or ability unless its controller pays {2}.
        this.addAbility(new BecomesTargetAnyTriggeredAbility(new CounterUnlessPaysEffect(new GenericManaCost(2))
                .setText("counter that spell or ability unless its controller pays {2}"),
                filter, StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS, SetTargetPointer.SPELL, false));

    }

    private JumpTrooper(final JumpTrooper card) {
        super(card);
    }

    @Override
    public JumpTrooper copy() {
        return new JumpTrooper(this);
    }
}
