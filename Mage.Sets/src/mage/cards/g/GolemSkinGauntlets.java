
package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author North
 */
public final class GolemSkinGauntlets extends CardImpl {

    public GolemSkinGauntlets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0 for each Equipment attached to it.
        // Equip 2 (2: Attach to target creature you control. Equip only as a sorcery. This card enters the battlefield unattached and stays on the battlefield if the creature leaves.)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(new GolemSkinGauntletsAttachedCount(), StaticValue.get(0), Duration.WhileOnBattlefield)));
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2)));
    }

    private GolemSkinGauntlets(final GolemSkinGauntlets card) {
        super(card);
    }

    @Override
    public GolemSkinGauntlets copy() {
        return new GolemSkinGauntlets(this);
    }
}

// we can't use GolemSkinGauntletsAttachedCount
// compare to Goblin Gaveleer
class GolemSkinGauntletsAttachedCount implements DynamicValue {

    GolemSkinGauntletsAttachedCount() {
    }

    private GolemSkinGauntletsAttachedCount(final GolemSkinGauntletsAttachedCount dynamicValue) {
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        Permanent equipment = game.getPermanent(sourceAbility.getSourceId());
        if (equipment != null) {
            Permanent permanent = game.getPermanent(equipment.getAttachedTo());
            if (permanent != null) {
                List<UUID> attachments = permanent.getAttachments();
                for (UUID attachmentId : attachments) {
                    Permanent attached = game.getPermanent(attachmentId);
                    if (attached != null && attached.hasSubtype(SubType.EQUIPMENT, game)) {
                        count++;
                    }
                }
            }

        }
        return count;
    }

    @Override
    public GolemSkinGauntletsAttachedCount copy() {
        return new GolemSkinGauntletsAttachedCount(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "Equipment attached to it";
    }
}