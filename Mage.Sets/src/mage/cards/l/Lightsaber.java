
package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public final class Lightsaber extends CardImpl {

    public Lightsaber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equiped creature gets +1/+0 and has firsttrike
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 0)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT)));

        // Equip 3
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3)));

        // Lightsaber's equip ability costs {1} if it targets a Jedi or Sith.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new InfoEffect("{this}'s equip ability costs {1} if it targets a Jedi or Sith")));

    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (ability instanceof EquipAbility) {
            Permanent targetCreature = game.getPermanent(ability.getTargets().getFirstTarget());
            if (targetCreature != null && (targetCreature.hasSubtype(SubType.SITH, game) || targetCreature.hasSubtype(SubType.JEDI, game))) {
                CardUtil.increaseCost(ability, 1 - ability.getManaCostsToPay().convertedManaCost());
            }
        }
    }

    public Lightsaber(final Lightsaber card) {
        super(card);
    }

    @Override
    public Lightsaber copy() {
        return new Lightsaber(this);
    }
}
