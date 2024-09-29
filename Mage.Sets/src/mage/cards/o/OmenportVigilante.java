package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CommittedCrimeCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.watchers.common.CommittedCrimeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OmenportVigilante extends CardImpl {

    public OmenportVigilante(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Omenport Vigilante has double strike as long as you've committed a crime this turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield),
                CommittedCrimeCondition.instance, "{this} has double strike as long as you've committed a crime this turn"
        )).addHint(CommittedCrimeCondition.getHint()), new CommittedCrimeWatcher());
    }

    private OmenportVigilante(final OmenportVigilante card) {
        super(card);
    }

    @Override
    public OmenportVigilante copy() {
        return new OmenportVigilante(this);
    }
}
