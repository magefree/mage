
package mage.cards.f;

import java.util.Optional;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.combat.CantAttackBlockAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 * @author LevelX2
 */
public final class FaithsFetters extends CardImpl {

    public FaithsFetters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant permanent
        TargetPermanent auraTarget = new TargetPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.LoseAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Faith's Fetters enters the battlefield, you gain 4 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(4)));

        // Enchanted permanent's activated abilities can't be activated unless they're mana abilities. If enchanted permanent is a creature, it can't attack or block.
        Effect effect = new CantAttackBlockAttachedEffect(AttachmentType.AURA);
        effect.setText("Enchanted permanent can't attack or block,");
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ability.addEffect(new FaithsFettersEffect());
        this.addAbility(ability);
    }

    private FaithsFetters(final FaithsFetters card) {
        super(card);
    }

    @Override
    public FaithsFetters copy() {
        return new FaithsFetters(this);
    }
}

class FaithsFettersEffect extends ContinuousRuleModifyingEffectImpl {

    public FaithsFettersEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "and its activated abilities can't be activated unless they're mana abilities";
    }

    public FaithsFettersEffect(final FaithsFettersEffect effect) {
        super(effect);
    }

    @Override
    public FaithsFettersEffect copy() {
        return new FaithsFettersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATE_ABILITY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.isAttachedTo(event.getSourceId())) {
            Optional<Ability> ability = game.getAbility(event.getTargetId(), event.getSourceId());
            if (ability.isPresent() && ability.get().getAbilityType() != AbilityType.MANA) {
                return true;
            }

        }
        return false;
    }
}
