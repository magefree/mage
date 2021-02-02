
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExertSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.WarriorVigilantToken;

/**
 *
 * @author spjspj
 */
public final class StewardOfSolidarity extends CardImpl {

    public StewardOfSolidarity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}, Exert Steward of Solidarity: Create a 1/1 white Warrior creature token with vigilance. 
        Effect effect = new CreateTokenEffect(new WarriorVigilantToken());
        effect.setText("Create a 1/1 white Warrior creature token with vigilance");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        ability.addCost(new ExertSourceCost());
        this.addAbility(ability);
    }

    private StewardOfSolidarity(final StewardOfSolidarity card) {
        super(card);
    }

    @Override
    public StewardOfSolidarity copy() {
        return new StewardOfSolidarity(this);
    }
}
