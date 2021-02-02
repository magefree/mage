
package mage.cards.e;

import java.util.UUID;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.BoostAllOfChosenSubtypeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class EngineeredPlague extends CardImpl {

    public EngineeredPlague(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}");

        // As Engineered Plague enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.UnboostCreature)));
        // All creatures of the chosen type get -1/-1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllOfChosenSubtypeEffect(-1, -1, Duration.WhileOnBattlefield, false)));
    }

    private EngineeredPlague(final EngineeredPlague card) {
        super(card);
    }

    @Override
    public EngineeredPlague copy() {
        return new EngineeredPlague(this);
    }
}
