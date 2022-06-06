
package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class BolaWarrior extends CardImpl {

    public BolaWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN, SubType.SPELLSHAPER, SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {R}, {tap}, Discard a card: Target creature can't block this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBlockTargetEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BolaWarrior(final BolaWarrior card) {
        super(card);
    }

    @Override
    public BolaWarrior copy() {
        return new BolaWarrior(this);
    }
}
