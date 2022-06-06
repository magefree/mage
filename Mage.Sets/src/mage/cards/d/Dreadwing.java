
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
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
 * @author Loki
 */
public final class Dreadwing extends CardImpl {

    public Dreadwing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{U}{R}: Dreadwing gets +3/+0 and gains flying until end of turn.
        Effect effect = new BoostSourceEffect(3, 0, Duration.EndOfTurn);
        effect.setText("{this} gets +3/+0");
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>(("{1}{U}{R}")));
        effect = new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains flying until end of turn");
        ability.getEffects().add(effect);
        this.addAbility(ability);
    }

    private Dreadwing(final Dreadwing card) {
        super(card);
    }

    @Override
    public Dreadwing copy() {
        return new Dreadwing(this);
    }

}
