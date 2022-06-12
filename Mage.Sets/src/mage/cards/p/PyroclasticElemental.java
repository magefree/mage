package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PyroclasticElemental extends CardImpl {

    public PyroclasticElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // {1}{R}{R}: Pyroclastic Elemental deals 1 damage to target player.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(1), new ManaCostsImpl<>("{1}{R}{R}")
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private PyroclasticElemental(final PyroclasticElemental card) {
        super(card);
    }

    @Override
    public PyroclasticElemental copy() {
        return new PyroclasticElemental(this);
    }
}
