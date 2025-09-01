package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AtomicMicrosizer extends CardImpl {

    public AtomicMicrosizer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{U}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 0)));

        // Whenever equipped creature attacks, choose up to one target creature. That creature can't be blocked this turn and has base power and toughness 1/1 until end of turn.
        Ability ability = new AttacksAttachedTriggeredAbility(new CantBeBlockedTargetEffect()
                .setText("choose up to one target creature. That creature can't be blocked this turn"));
        ability.addEffect(new SetBasePowerToughnessTargetEffect(1, 1, Duration.EndOfTurn)
                .setText("and has base power and toughness 1/1 until end of turn"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private AtomicMicrosizer(final AtomicMicrosizer card) {
        super(card);
    }

    @Override
    public AtomicMicrosizer copy() {
        return new AtomicMicrosizer(this);
    }
}
