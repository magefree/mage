
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class KozileksShrieker extends CardImpl {

    public KozileksShrieker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        // {C}: Kozilek's Shrieker gets +1/+0 and gains menace until end of turn.
        Effect effect = new BoostSourceEffect(1, 0, Duration.EndOfTurn);
        effect.setText("{this} gets +1/+0");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{C}"));
        effect = new GainAbilitySourceEffect(new MenaceAbility(), Duration.EndOfTurn);
        effect.setText("and gains menace until end of turn. " +
                "<i>(It can't be blocked except by two or more creatures. {C} represents colorless mana.)</i>");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private KozileksShrieker(final KozileksShrieker card) {
        super(card);
    }

    @Override
    public KozileksShrieker copy() {
        return new KozileksShrieker(this);
    }
}
