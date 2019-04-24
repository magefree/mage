
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class SkyblinderStaff extends CardImpl {

    public SkyblinderStaff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0 and can't be blocked by creatures with flying.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 0));
        ability.addEffect(new CantBeBlockedByCreaturesWithFlyingAttachedEffect());
        this.addAbility(ability);
        
        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3)));

    }

    public SkyblinderStaff(final SkyblinderStaff card) {
        super(card);
    }

    @Override
    public SkyblinderStaff copy() {
        return new SkyblinderStaff(this);
    }
}

class CantBeBlockedByCreaturesWithFlyingAttachedEffect extends RestrictionEffect {

    public CantBeBlockedByCreaturesWithFlyingAttachedEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Equipped creature can't be blocked by creatures with flying";
    }

    public CantBeBlockedByCreaturesWithFlyingAttachedEffect(final CantBeBlockedByCreaturesWithFlyingAttachedEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return game.getPermanent(source.getSourceId()) != null
                && permanent.getAttachments().contains(source.getSourceId());
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return !blocker.getAbilities().contains(FlyingAbility.getInstance());
    }

    @Override
    public CantBeBlockedByCreaturesWithFlyingAttachedEffect copy() {
        return new CantBeBlockedByCreaturesWithFlyingAttachedEffect(this);
    }
}
