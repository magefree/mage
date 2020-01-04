package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PiousWayfarer extends CardImpl {

    public PiousWayfarer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Constellation — Whenever an enchantment enters the battlefield under your control, target creature gets +1/+1 until end of turn.
        Ability ability = new ConstellationAbility(
                new BoostTargetEffect(1, 1), false, false
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private PiousWayfarer(final PiousWayfarer card) {
        super(card);
    }

    @Override
    public PiousWayfarer copy() {
        return new PiousWayfarer(this);
    }
}
