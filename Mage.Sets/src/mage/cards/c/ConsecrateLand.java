
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author MTGfan
 */
public final class ConsecrateLand extends CardImpl {

    public ConsecrateLand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted land is indestructible and can't be enchanted by other Auras.
        Ability ability2 = new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(IndestructibleAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield, "Enchanted land is indestructible"));
        ability2.addEffect(new ConsecrateLandRuleEffect());
        this.addAbility(ability2);
    }

    private ConsecrateLand(final ConsecrateLand card) {
        super(card);
    }

    @Override
    public ConsecrateLand copy() {
        return new ConsecrateLand(this);
    }
}

// 9/25/2006 ruling: If Consecrate Land enters the battlefield attached to a land that's enchanted by other Auras, those Auras are put into their owners' graveyards.
class ConsecrateLandRuleEffect extends ContinuousRuleModifyingEffectImpl {

    public ConsecrateLandRuleEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "and can't be enchanted by other Auras";
    }

    public ConsecrateLandRuleEffect(final ConsecrateLandRuleEffect effect) {
        super(effect);
    }

    @Override
    public ConsecrateLandRuleEffect copy() {
        return new ConsecrateLandRuleEffect(this);
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
