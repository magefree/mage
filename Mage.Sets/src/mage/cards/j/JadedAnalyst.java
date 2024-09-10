package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.LoseAbilitySourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JadedAnalyst extends CardImpl {

    public JadedAnalyst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Whenever you draw your second card each turn, Jaded Analyst loses defender and gains vigilance until end of turn.
        Ability ability = new DrawNthCardTriggeredAbility(new LoseAbilitySourceEffect(
                DefenderAbility.getInstance(), Duration.EndOfTurn
        ).setText("{this} loses defender"), false, 2);
        ability.addEffect(new GainAbilitySourceEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains vigilance until end of turn"));
        this.addAbility(ability);
    }

    private JadedAnalyst(final JadedAnalyst card) {
        super(card);
    }

    @Override
    public JadedAnalyst copy() {
        return new JadedAnalyst(this);
    }
}
