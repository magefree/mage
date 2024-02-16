
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Galatolol
 */
public final class FlowstoneThopter extends CardImpl {

    public FlowstoneThopter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");
        
        this.subtype.add(SubType.THOPTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {1}: Flowstone Thopter gets +1/-1 and gains flying until end of turn.
        Effect effect = new BoostSourceEffect(1, -1, Duration.EndOfTurn);
        effect.setText("{this} gets +1/-1");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{1}"));
        effect = new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains flying until end of turn.");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private FlowstoneThopter(final FlowstoneThopter card) {
        super(card);
    }

    @Override
    public FlowstoneThopter copy() {
        return new FlowstoneThopter(this);
    }
}
