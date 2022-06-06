package mage.cards.s;

import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScavengedBlade extends CardImpl {

    public ScavengedBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Scavenged Blade enters the battlefield, attach it to target creature you control.
        this.addAbility(new EntersBattlefieldAttachToTarget());

        // Equipped creature gets +2/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 0)));

        // Equip {2}{R}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{2}{R}")));
    }

    private ScavengedBlade(final ScavengedBlade card) {
        super(card);
    }

    @Override
    public ScavengedBlade copy() {
        return new ScavengedBlade(this);
    }
}
