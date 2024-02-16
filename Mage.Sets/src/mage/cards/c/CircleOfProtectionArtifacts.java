
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceToYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterObject;

/**
 *
 * @author Plopman
 */
public final class CircleOfProtectionArtifacts extends CardImpl {

    private static final FilterObject filter = new FilterObject("artifact source");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }
    
    public CircleOfProtectionArtifacts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");

        // {2}: The next time an artifact source of your choice would deal damage to you this turn, prevent that damage.
        Effect effect = new PreventNextDamageFromChosenSourceToYouEffect(Duration.EndOfTurn, filter);
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{2}")));
    }

    private CircleOfProtectionArtifacts(final CircleOfProtectionArtifacts card) {
        super(card);
    }

    @Override
    public CircleOfProtectionArtifacts copy() {
        return new CircleOfProtectionArtifacts(this);
    }
}
