package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author miesma
 */
public final class WizardsStaff extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(SubType.WIZARD, "Wizard");

    public WizardsStaff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has prowess.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new ProwessAbility(), AttachmentType.EQUIPMENT
        )));

        // If a triggered ability of equipped creature triggers,
        // that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new WizardsStaffEffect()));

        // Equip Wizard {1}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1), new TargetPermanent(filter)));

        // Equip {3}
        this.addAbility(new EquipAbility(3, false));
    }

    private WizardsStaff(final WizardsStaff card) {
        super(card);
    }

    @Override
    public WizardsStaff copy() {
        return new WizardsStaff(this);
    }
}

class WizardsStaffEffect extends ReplacementEffectImpl {

    WizardsStaffEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if a triggered ability of equipped creature triggers, that ability triggers an additional time";
    }

    private WizardsStaffEffect(final WizardsStaffEffect effect) {
        super(effect);
    }

    @Override
    public WizardsStaffEffect copy() {
        return new WizardsStaffEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        Permanent equipment = game.getPermanent(source.getSourceId());
        return permanent != null && equipment != null
                && permanent.getId().equals(equipment.getAttachedTo());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}

