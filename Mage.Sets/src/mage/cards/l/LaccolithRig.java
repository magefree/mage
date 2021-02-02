package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.AssignNoCombatDamageSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class LaccolithRig extends CardImpl {

    public LaccolithRig(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted creature becomes blocked, you may have it deal damage equal to its power to target creature. If you do, the first creature assigns no combat damage this turn.
        Ability ability2 = new BecomesBlockedAttachedTriggeredAbility(new LaccolithRigEffect(), true);
        ability2.addTarget(new TargetCreaturePermanent());
        Effect effect = new GainAbilityTargetEffect(new SimpleStaticAbility(Zone.BATTLEFIELD, new AssignNoCombatDamageSourceEffect(Duration.Custom, true).setText("")), Duration.EndOfTurn, "If you do, the first creature assigns no combat damage this turn");
        ability2.addEffect(effect);
        this.addAbility(ability2);
    }

    private LaccolithRig(final LaccolithRig card) {
        super(card);
    }

    @Override
    public LaccolithRig copy() {
        return new LaccolithRig(this);
    }
}

class LaccolithRigEffect extends OneShotEffect {

    public LaccolithRigEffect() {
        super(Outcome.Damage);
        this.staticText = "you may have it deal damage equal to its power to target creature";
    }

    public LaccolithRigEffect(final LaccolithRigEffect effect) {
        super(effect);
    }

    @Override
    public LaccolithRigEffect copy() {
        return new LaccolithRigEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // In the case that the enchantment is blinked
        Permanent enchantment = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        if (enchantment == null) {
            // It was not blinked, use the standard method
            enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        }
        if (enchantment == null) {
            return false;
        }
        Permanent ownCreature = game.getPermanent(enchantment.getAttachedTo());
        if (ownCreature != null) {
            int damage = ownCreature.getPower().getValue();
            Permanent targetCreature = game.getPermanent(source.getFirstTarget());
            if (targetCreature != null) {
                targetCreature.damage(damage, ownCreature.getId(), source, game, false, true);
                return true;
            }
        }
        return false;
    }
}
