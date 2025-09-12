package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GhostSpiderGwenStacy extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_ATTACKING_CREATURE);
    private static final Hint hint = new ValueHint("Attacking creatures", xValue);

    public GhostSpiderGwenStacy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Ghost-Spider attacks, she deals X damage to defending player, where X is the number of attacking creatures.
        this.addAbility(new AttacksTriggeredAbility(
                new DamageTargetEffect(xValue)
                        .setText("she deals X damage to defending player, where X is the number of attacking creatures"),
                false, null, SetTargetPointer.PLAYER
        ).addHint(hint));
    }

    private GhostSpiderGwenStacy(final GhostSpiderGwenStacy card) {
        super(card);
    }

    @Override
    public GhostSpiderGwenStacy copy() {
        return new GhostSpiderGwenStacy(this);
    }
}
