package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.BoastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HagiMob extends CardImpl {

    public HagiMob(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Boast — {1}{R}: Hagi Mob deals 1 damage to any target.
        Ability ability = new BoastAbility(new DamageTargetEffect(1), "{1}{R}");
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private HagiMob(final HagiMob card) {
        super(card);
    }

    @Override
    public HagiMob copy() {
        return new HagiMob(this);
    }
}
