package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantAttackBlockAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IntercessorsArrest extends CardImpl {

    public IntercessorsArrest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant permanent
        TargetPermanent auraTarget = new TargetPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Enchanted permanent can't attack, block, or crew Vehicles. Its activated abilities can't be activated unless they're mana abilities.
        Ability ability = new SimpleStaticAbility(new CantAttackBlockAttachedEffect(AttachmentType.AURA)
                .setText("Enchanted permanent can't attack, block"));
        ability.addEffect(new IntercessorsArrestEffect());
        this.addAbility(ability);
    }

    private IntercessorsArrest(final IntercessorsArrest card) {
        super(card);
    }

    @Override
    public IntercessorsArrest copy() {
        return new IntercessorsArrest(this);
    }
}

class IntercessorsArrestEffect extends ContinuousRuleModifyingEffectImpl {

    public IntercessorsArrestEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = ", or crew Vehicles. Its activated abilities can't be activated unless they're mana abilities";
    }

    private IntercessorsArrestEffect(final IntercessorsArrestEffect effect) {
        super(effect);
    }

    @Override
    public IntercessorsArrestEffect copy() {
        return new IntercessorsArrestEffect(this);
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
        if (enchantment == null) {
            return false;
        }
        switch (event.getType()) {
            case CREW_VEHICLE:
                return enchantment.isAttachedTo(event.getTargetId());
            case ACTIVATE_ABILITY:
                if (enchantment.isAttachedTo(event.getSourceId())) {
                    Optional<Ability> ability = game.getAbility(event.getTargetId(), event.getSourceId());
                    return ability.isPresent() && ability.get().getAbilityType() != AbilityType.MANA;
                }
        }
        return false;
    }
}
