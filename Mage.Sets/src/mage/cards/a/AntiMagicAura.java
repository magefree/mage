
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CantBeTargetedAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author MTGfan & L_J
 */
public final class AntiMagicAura extends CardImpl {

    public AntiMagicAura(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature can't be the target of spells and can't be enchanted by other Auras.
        CantBeTargetedAttachedEffect cantTargetEffect = new CantBeTargetedAttachedEffect(new FilterSpell("spells"), Duration.WhileOnBattlefield, AttachmentType.AURA, TargetController.ANY);
        Ability ability2 = new SimpleStaticAbility(Zone.BATTLEFIELD, cantTargetEffect);
        ability2.addEffect(new AntiMagicAuraRuleEffect());
        this.addAbility(ability2);
    }

    private AntiMagicAura(final AntiMagicAura card) {
        super(card);
    }

    @Override
    public AntiMagicAura copy() {
        return new AntiMagicAura(this);
    }
}

// 9/25/2006 ruling: If Consecrate Land enters the battlefield attached to a land that's enchanted by other Auras, those Auras are put into their owners' graveyards.
class AntiMagicAuraRuleEffect extends ContinuousRuleModifyingEffectImpl {

    public AntiMagicAuraRuleEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "and can't be enchanted by other Auras";
    }

    public AntiMagicAuraRuleEffect(final AntiMagicAuraRuleEffect effect) {
        super(effect);
    }

    @Override
    public AntiMagicAuraRuleEffect copy() {
        return new AntiMagicAuraRuleEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACH || event.getType() == GameEvent.EventType.STAY_ATTACHED;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (sourceObject != null && sourceObject.getAttachedTo() != null) {
            if (event.getTargetId().equals(sourceObject.getAttachedTo())) {
                return !event.getSourceId().equals(source.getSourceId());
            }
        }
        return false;
    }
}
