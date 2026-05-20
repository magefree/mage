package mage.cards.e;

import mage.MageInt;
import mage.abilities.abilityword.OpusAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExpressiveFiredancer extends CardImpl {

    public ExpressiveFiredancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Opus -- Whenever you cast an instant or sorcery spell, this creature gets +1/+1 until end of turn. If five or more mana was spent to cast that spell, this creature also gains double strike until end of turn.
        this.addAbility(new OpusAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn),
                new GainAbilitySourceEffect(
                        DoubleStrikeAbility.getInstance(), Duration.EndOfTurn
                ).setText("this creature also gains double strike until end of turn"),
                null, false
        ));
    }

    private ExpressiveFiredancer(final ExpressiveFiredancer card) {
        super(card);
    }

    @Override
    public ExpressiveFiredancer copy() {
        return new ExpressiveFiredancer(this);
    }
}
