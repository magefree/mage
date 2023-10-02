
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author emerald000
 */
public final class AvariceAmulet extends CardImpl {

    public AvariceAmulet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0
        Effect effect = new BoostEquippedEffect(2, 0);
        effect.setText("Equipped creature gets +2/+0");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        // and has vigilance
        effect = new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.EQUIPMENT);
        effect.setText("and has vigilance");
        ability.addEffect(effect);
        //and "At the beginning of your upkeep, draw a card."
        effect = new GainAbilityAttachedEffect(new BeginningOfUpkeepTriggeredAbility(new DrawCardSourceControllerEffect(1), TargetController.YOU, false), AttachmentType.EQUIPMENT);
        effect.setText("and \"At the beginning of your upkeep, draw a card.\"");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Whenever equipped creature dies, target opponent gains control of Avarice Amulet.
        ability = new DiesAttachedTriggeredAbility(new AvariceAmuletChangeControlEffect(), "equipped creature", false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    private AvariceAmulet(final AvariceAmulet card) {
        super(card);
    }

    @Override
    public AvariceAmulet copy() {
        return new AvariceAmulet(this);
    }
}

class AvariceAmuletChangeControlEffect extends ContinuousEffectImpl {

    AvariceAmuletChangeControlEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "target opponent gains control of {this}";
    }

    private AvariceAmuletChangeControlEffect(final AvariceAmuletChangeControlEffect effect) {
        super(effect);
    }

    @Override
    public AvariceAmuletChangeControlEffect copy() {
        return new AvariceAmuletChangeControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            return permanent.changeControllerId(source.getFirstTarget(), game, source);
        }
        return false;
    }

}
