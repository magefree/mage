
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author Quercitron
 */
public final class AltacBloodseeker extends CardImpl {
    
    public AltacBloodseeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever a creature an opponent controls dies, Altac Bloodseeker gets +2/+0 and gains first strike and haste until end of turn.
        Effect effect = new BoostSourceEffect(2, 0, Duration.EndOfTurn);
        effect.setText("{this} gets +2/+0");
        Ability ability = new DiesCreatureTriggeredAbility(effect, false, StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE);
        
        effect = new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains first strike");
        ability.addEffect(effect);
        
        effect = new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and haste until end of turn");
        ability.addEffect(effect);
        
        this.addAbility(ability);
    }

    private AltacBloodseeker(final AltacBloodseeker card) {
        super(card);
    }

    @Override
    public AltacBloodseeker copy() {
        return new AltacBloodseeker(this);
    }
}
