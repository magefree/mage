
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author fireshoes
 */
public final class VulshokGauntlets extends CardImpl {

    public VulshokGauntlets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +4/+2 and doesn't untap during its controller's untap step.
        Effect effect = new BoostEquippedEffect(4, 2);
        effect.setText("Equipped creature gets +4/+2");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new VulshokGauntletsEffect();
        effect.setText("and has doesn't untap during its controller's untap step");
        ability.addEffect(effect);
        this.addAbility(ability);
        
        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(3)));
    }

    private VulshokGauntlets(final VulshokGauntlets card) {
        super(card);
    }

    @Override
    public VulshokGauntlets copy() {
        return new VulshokGauntlets(this);
    }
}

class VulshokGauntletsEffect extends ReplacementEffectImpl {

    public VulshokGauntletsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Equipped creature doesn't untap during its controller's untap step";
    }

    public VulshokGauntletsEffect(final VulshokGauntletsEffect effect) {
        super(effect);
    }

    @Override
    public VulshokGauntletsEffect copy() {
        return new VulshokGauntletsEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getTurnStepType() == PhaseStep.UNTAP) {
            Permanent equipment = game.getPermanent(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null) {
                Permanent equipped = game.getPermanent(equipment.getAttachedTo());
                if (equipped.getId().equals(event.getTargetId())) {
                    return true;
                }
            }
        }
        return false;
    }
}