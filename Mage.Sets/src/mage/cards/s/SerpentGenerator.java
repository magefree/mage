
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.SerpentGeneratorSnakeToken;

/**
 *
 * @author LoneFox
 */
public final class SerpentGenerator extends CardImpl {

    public SerpentGenerator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // {4}, {tap}: Create a 1/1 colorless Snake artifact creature token. It has "Whenever this creature deals damage to a player, that player gets a poison counter."
        Effect effect = new CreateTokenEffect(new SerpentGeneratorSnakeToken());
        effect.setText("Create a 1/1 colorless Snake artifact creature token. It has \"Whenever this creature deals damage to a player, that player gets a poison counter.\"");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{4}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private SerpentGenerator(final SerpentGenerator card) {
        super(card);
    }

    @Override
    public SerpentGenerator copy() {
        return new SerpentGenerator(this);
    }
}
