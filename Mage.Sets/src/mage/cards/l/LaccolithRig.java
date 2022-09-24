package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.AssignNoCombatDamageTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author awjackson
 */
public final class LaccolithRig extends CardImpl {

    public LaccolithRig(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Whenever enchanted creature becomes blocked, you may have it deal damage equal to its power to target creature.
        // If you do, the first creature assigns no combat damage this turn.
        Ability ability = new BecomesBlockedAttachedTriggeredAbility(new LaccolithRigEffect(), true, SetTargetPointer.PERMANENT);
        ability.addEffect(new AssignNoCombatDamageTargetEffect(Duration.EndOfTurn, "if you do, the first creature assigns no combat damage this turn"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
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
        this.staticText = "it deal damage equal to its power to target creature";
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
        Permanent ownCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent targetCreature = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        if (ownCreature == null || targetCreature == null) {
            return false;
        }
        targetCreature.damage(ownCreature.getPower().getValue(), ownCreature.getId(), source, game, false, true);
        return true;
    }
}
