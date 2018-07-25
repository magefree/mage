package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author NinthWorld
 */
public final class Hellion extends CardImpl {

    public Hellion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{R}");
        
        this.subtype.add(SubType.TERRAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {R}: Hellion gets +1/+0 and gains haste until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn).setText("{this} gets +1/+0"), new ManaCostsImpl("{R}"));
        ability.addEffect(new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.EndOfTurn).setText("and gains haste until end of turn"));
        this.addAbility(ability);
    }

    public Hellion(final Hellion card) {
        super(card);
    }

    @Override
    public Hellion copy() {
        return new Hellion(this);
    }
}
