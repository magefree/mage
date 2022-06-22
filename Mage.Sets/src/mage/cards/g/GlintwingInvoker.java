
package mage.cards.g;

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
 * @author LoneFox
 */
public final class GlintwingInvoker extends CardImpl {

    public GlintwingInvoker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {7}{U}: Glintwing Invoker gets +3/+3 and gains flying until end of turn.
        Effect effect = new BoostSourceEffect(3, 3, Duration.EndOfTurn);
        effect.setText("{this} gets +3/+3");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{7}{U}"));
        effect = new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains flying until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private GlintwingInvoker(final GlintwingInvoker card) {
        super(card);
    }

    @Override
    public GlintwingInvoker copy() {
        return new GlintwingInvoker(this);
    }
}
