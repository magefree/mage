package mage.cards.b;

import java.util.Optional;
import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.combat.CantAttackBlockAttachedEffect;
import mage.constants.*;
import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 *
 * @author weirddan455
 */
public final class BoundInGold extends CardImpl {

    public BoundInGold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant permanent
        TargetPermanent auraTarget = new TargetPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Removal));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted permanent can't attack, block, or crew Vehicles,
        // and its activated abilities can't be activated unless they're mana abilities.
        ability = new SimpleStaticAbility(new CantAttackBlockAttachedEffect(AttachmentType.AURA)
                .setText("Enchanted permanent can't attack, block"));
        ability.addEffect(new BoundInGoldEffect());
        this.addAbility(ability);
    }

    private BoundInGold(final BoundInGold card) {
        super(card);
    }

    @Override
    public BoundInGold copy() {
        return new BoundInGold(this);
    }
}

class BoundInGoldEffect extends ContinuousRuleModifyingEffectImpl {

    public BoundInGoldEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = ", or crew Vehicles, and its activated abilities can't be activated unless they're mana abilities";
    }

    private BoundInGoldEffect(final BoundInGoldEffect effect) {
        super(effect);
    }

    @Override
    public BoundInGoldEffect copy() {
        return new BoundInGoldEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case CREW_VEHICLE:
            case ACTIVATE_ABILITY:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null) {
            switch (event.getType()) {
                case CREW_VEHICLE:
                    return enchantment.isAttachedTo(event.getTargetId());
                case ACTIVATE_ABILITY:
                    if (enchantment.isAttachedTo(event.getSourceId())) {
                        Optional<Ability> ability = game.getAbility(event.getTargetId(), event.getSourceId());
                        return ability.isPresent() && ability.get().getAbilityType() != AbilityType.MANA;
                    }
            }
        }
        return false;
    }
}
