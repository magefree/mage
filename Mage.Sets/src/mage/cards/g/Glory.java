
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainProtectionFromColorAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LoneFox
 */
public final class Glory extends CardImpl {

    public Glory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.INCARNATION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {2}{W}: Choose a color. Creatures you control gain protection from the chosen color until end of turn. Activate this ability only if Glory is in your graveyard.
        Effect effect = new GainProtectionFromColorAllEffect(Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES);
        effect.setText("Choose a color. Creatures you control gain protection from the chosen color until end of turn. Activate only if {this} is in your graveyard.");
        this.addAbility(new SimpleActivatedAbility(Zone.GRAVEYARD, effect, new ManaCostsImpl("{2}{W}")));
    }

    private Glory(final Glory card) {
        super(card);
    }

    @Override
    public Glory copy() {
        return new Glory(this);
    }
}
