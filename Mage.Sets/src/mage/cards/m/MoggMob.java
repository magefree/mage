package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTargetAmount;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoggMob extends CardImpl {

    public MoggMob(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{R}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Sacrifice Mogg Mob: It deals 3 damage divided as you choose among one, two, or three targets.
        Ability ability = new SimpleActivatedAbility(
                new DamageMultiEffect(3, "it"), new SacrificeSourceCost()
        );
        ability.addTarget(new TargetAnyTargetAmount(3));
        this.addAbility(ability);
    }

    private MoggMob(final MoggMob card) {
        super(card);
    }

    @Override
    public MoggMob copy() {
        return new MoggMob(this);
    }
}
