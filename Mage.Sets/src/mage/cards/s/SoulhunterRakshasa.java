package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetOpponent;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class SoulhunterRakshasa extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.SWAMP));

    public SoulhunterRakshasa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Soulhunter Rakshasa can’t block.
        this.addAbility(new CantBlockAbility());

        // When Soulhunter Rakshasa enters the battlefield, if you cast it from your hand, it deals 1 damage to target opponent for each Swamp you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(xValue))
                .withInterveningIf(CastFromHandSourcePermanentCondition.instance)
                .withRuleTextReplacement(true);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability, new CastFromHandWatcher());
    }

    private SoulhunterRakshasa(final SoulhunterRakshasa card) {
        super(card);
    }

    @Override
    public SoulhunterRakshasa copy() {
        return new SoulhunterRakshasa(this);
    }
}
