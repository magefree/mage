package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetAndSelfEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class PsionicEntity extends CardImpl {

    public PsionicEntity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Psionic Entity deals 2 damage to any target and 3 damage to itself.
        Ability ability = new SimpleActivatedAbility(new DamageTargetAndSelfEffect(2, 3), new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private PsionicEntity(final PsionicEntity card) {
        super(card);
    }

    @Override
    public PsionicEntity copy() {
        return new PsionicEntity(this);
    }
}
