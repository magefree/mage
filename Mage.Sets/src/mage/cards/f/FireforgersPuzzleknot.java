
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author emerald000
 */
public final class FireforgersPuzzleknot extends CardImpl {

    public FireforgersPuzzleknot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // When Fireforger's Puzzleknot enters the battlefield, it deals 1 damage to any target.

        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(1, "it"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // {2}{R}, Sacrifice Fireforger's Puzzleknot: It deals 1 damage to any target.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1, "It"), new ManaCostsImpl<>("{2}{R}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    public FireforgersPuzzleknot(final FireforgersPuzzleknot card) {
        super(card);
    }

    @Override
    public FireforgersPuzzleknot copy() {
        return new FireforgersPuzzleknot(this);
    }
}
