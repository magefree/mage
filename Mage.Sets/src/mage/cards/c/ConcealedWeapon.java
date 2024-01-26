package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConcealedWeapon extends CardImpl {

    public ConcealedWeapon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +3/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(3, 0)));

        // Disguise {2}{R}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{2}{R}")));

        // When Concealed Weapon is turned face up, attach it to target creature you control.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new AttachEffect(
                Outcome.BoostCreature, "attach it to target creature you control"
        ));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Equip {1}{R}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{1}{R}")));
    }

    private ConcealedWeapon(final ConcealedWeapon card) {
        super(card);
    }

    @Override
    public ConcealedWeapon copy() {
        return new ConcealedWeapon(this);
    }
}
