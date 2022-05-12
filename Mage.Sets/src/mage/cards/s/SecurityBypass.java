package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SecurityBypass extends CardImpl {

    public SecurityBypass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // As long as enchanted creature is attacking alone, it can't be blocked.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantBeBlockedAttachedEffect(AttachmentType.AURA), SecurityBypassCondition.instance,
                "as long as enchanted creature is attacking alone, it can't be blocked"
        )));

        // Enchanted creature has "Whenever this creature deals combat damage to a player, it connives."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new DealsCombatDamageToAPlayerTriggeredAbility(new ConniveSourceEffect(), false)
                        .setTriggerPhrase("Whenever this creature deals combat damage to a player, "),
                AttachmentType.AURA, Duration.WhileOnBattlefield, "enchanted creature has " +
                "\"Whenever this creature deals combat damage to a player, it connives.\" " +
                "<i>(Its controller draws a card, then discards a card. If they discarded a nonland card, " +
                "they put a +1/+1 counter on this creature.)</i>"
        )));
    }

    private SecurityBypass(final SecurityBypass card) {
        super(card);
    }

    @Override
    public SecurityBypass copy() {
        return new SecurityBypass(this);
    }
}

enum SecurityBypassCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent aura = source.getSourcePermanentIfItStillExists(game);
        return aura != null
                && game.getCombat().attacksAlone()
                && game.getCombat().getAttackers().contains(aura.getAttachedTo());
    }
}
