
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author cbt33
 */
public final class DwarvenStrikeForce extends CardImpl {

    public DwarvenStrikeForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.BERSERKER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Discard a card at random: Dwarven Strike Force gains first strike and haste until end of turn.
        Effect effect = new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("{this} gains first strike");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new DiscardCardCost(true));
        effect = new GainAbilitySourceEffect(HasteAbility.getInstance(),Duration.EndOfTurn);
        effect.setText("and haste until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private DwarvenStrikeForce(final DwarvenStrikeForce card) {
        super(card);
    }

    @Override
    public DwarvenStrikeForce copy() {
        return new DwarvenStrikeForce(this);
    }
}
