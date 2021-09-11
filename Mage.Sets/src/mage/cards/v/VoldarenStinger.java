package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceAttackingCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VoldarenStinger extends CardImpl {

    public VoldarenStinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Voldaren Stinger has first strike as long as it's attacking.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield),
                SourceAttackingCondition.instance, "{this} has first strike as long as it's attacking"
        )));

        // {2}{R}: Voldaren Stinger gets +2/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostSourceEffect(
                2, 0, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{2}{R}")));
    }

    private VoldarenStinger(final VoldarenStinger card) {
        super(card);
    }

    @Override
    public VoldarenStinger copy() {
        return new VoldarenStinger(this);
    }
}
