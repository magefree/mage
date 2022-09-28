package mage.cards.c;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class Contempt extends CardImpl {

    public Contempt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever enchanted creature attacks, return it and Contempt to their owners' hands at end of combat.
        this.addAbility(new AttacksAttachedTriggeredAbility(new ContemptEffect()));

    }

    private Contempt(final Contempt card) {
        super(card);
    }

    @Override
    public Contempt copy() {
        return new Contempt(this);
    }
}

class ContemptEffect extends OneShotEffect {

    ContemptEffect() {
        super(Outcome.Detriment);
        this.staticText = "return it and {this} to their owners' hands at end of combat.";
    }

    ContemptEffect(final ContemptEffect effect) {
        super(effect);
    }

    @Override
    public ContemptEffect copy() {
        return new ContemptEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent contempt = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (contempt != null) {
            Permanent attachedToPermanent = game.getPermanent(contempt.getAttachedTo());
            if (attachedToPermanent != null) {
                Effect effect = new ReturnToHandTargetEffect();
                effect.setTargetPointer(new FixedTarget(
                        attachedToPermanent.getId(), game)).setText("return "
                        + attachedToPermanent.getName() + " to owner's hand.");
                AtTheEndOfCombatDelayedTriggeredAbility ability = new AtTheEndOfCombatDelayedTriggeredAbility(effect);
                game.addDelayedTriggeredAbility(ability, source);
            }
            Effect effect = new ReturnToHandSourceEffect();
            AtTheEndOfCombatDelayedTriggeredAbility ability = new AtTheEndOfCombatDelayedTriggeredAbility(effect);
            game.addDelayedTriggeredAbility(ability, source);
            return true;
        }
        return false;
    }
}
