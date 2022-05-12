
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class GrapplingHook extends CardImpl {

    public GrapplingHook(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has double strike.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(DoubleStrikeAbility.getInstance(), AttachmentType.EQUIPMENT)));
        // Whenever equipped creature attacks, you may have target creature block it this turn if able.
        Ability ability = new AttacksAttachedTriggeredAbility(new GrapplingHookEffect(), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(4), false));
    }

    private GrapplingHook(final GrapplingHook card) {
        super(card);
    }

    @Override
    public GrapplingHook copy() {
        return new GrapplingHook(this);
    }
}

class GrapplingHookEffect extends RequirementEffect {

    public GrapplingHookEffect() {
        this(Duration.EndOfTurn);
    }

    public GrapplingHookEffect(Duration duration) {
        super(duration);
        staticText = "target creature block it this turn if able";
    }

    public GrapplingHookEffect(final GrapplingHookEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getFirstTarget())) {
            Permanent equipment = game.getPermanent(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null) {
                return permanent.canBlock(equipment.getAttachedTo(), game);
            }
        }
        return false;
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return true;
    }

    @Override
    public UUID mustBlockAttacker(Ability source, Game game) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        return attachment != null ? attachment.getAttachedTo() : null;
    }

    @Override
    public GrapplingHookEffect copy() {
        return new GrapplingHookEffect(this);
    }
}
