package mage.cards.c;

import java.util.UUID;

import mage.constants.*;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.AttacksOrBlocksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextCleanupDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class Cunning extends CardImpl {

    public Cunning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +3/+3.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(3, 3)));

        // When enchanted creature attacks or blocks, sacrifice Cunning at the beginning of the next cleanup step.
        this.addAbility(new AttacksOrBlocksAttachedTriggeredAbility(
                new SacrificeSourceBeginningCleanupStepEffect(), AttachmentType.AURA));
    }

    private Cunning(final Cunning card) {
        super(card);
    }

    @Override
    public Cunning copy() {
        return new Cunning(this);
    }
}

class SacrificeSourceBeginningCleanupStepEffect extends OneShotEffect {

    public SacrificeSourceBeginningCleanupStepEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "sacrifice {this} at the beginning of the next cleanup step";
    }

    public SacrificeSourceBeginningCleanupStepEffect(final SacrificeSourceBeginningCleanupStepEffect effect) {
        super(effect);
    }

    @Override
    public SacrificeSourceBeginningCleanupStepEffect copy() {
        return new SacrificeSourceBeginningCleanupStepEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent cunning = game.getPermanent(source.getSourceId());
        if (cunning != null) {
            DelayedTriggeredAbility delayedAbility
                    = new AtTheBeginOfNextCleanupDelayedTriggeredAbility(
                            new SacrificeSourceEffect());
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }
}
