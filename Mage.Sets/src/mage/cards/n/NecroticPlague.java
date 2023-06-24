
package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class NecroticPlague extends CardImpl {

    public NecroticPlague(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature has "At the beginning of your upkeep, sacrifice this creature."
        // When enchanted creature dies, its controller chooses target creature one of their opponents controls. Return Necrotic Plague from its owner's graveyard to the battlefield attached to that creature.
        ability = new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceEffect(), TargetController.YOU, false);
        Effect effect = new GainAbilityAttachedEffect(ability, AttachmentType.AURA, Duration.WhileOnBattlefield);
        effect.setText("Enchanted creature has \"At the beginning of your upkeep, sacrifice this creature.\"");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        ability = new DiesAttachedTriggeredAbility(new NecroticPlagueEffect(), "enchanted creature", false);
        ability.setTargetAdjuster(NecroticPlagueAdjuster.instance);
        this.addAbility(ability);

    }

    private NecroticPlague(final NecroticPlague card) {
        super(card);
    }

    @Override
    public NecroticPlague copy() {
        return new NecroticPlague(this);
    }

}

enum NecroticPlagueAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Permanent attachedTo = null;
        for (Effect effect : ability.getEffects()) {
            attachedTo = (Permanent) effect.getValue("attachedTo");
        }
        if (attachedTo == null) {
            return;
        }
        Player creatureController = game.getPlayer(attachedTo.getControllerId());
        if (creatureController == null) {
            return;
        }
        ability.setControllerId(creatureController.getId());
        ability.getTargets().clear();
        TargetPermanent target = new TargetOpponentsCreaturePermanent();
        target.setTargetController(creatureController.getId());
        ability.getTargets().add(target);
    }
}

class NecroticPlagueEffect extends OneShotEffect {

    public NecroticPlagueEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "its controller chooses target creature one of their opponents controls. " +
                "Return {this} from its owner's graveyard to the battlefield attached to that creature";
    }

    public NecroticPlagueEffect(final NecroticPlagueEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent attachedTo = (Permanent) this.getValue("attachedTo");
        if (attachedTo != null) {
            Player creatureController = game.getPlayer(attachedTo.getControllerId());
            if (creatureController != null) {
                Card sourceEnchantmentCard = game.getCard(source.getSourceId());
                Permanent creature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
                if (sourceEnchantmentCard != null && creature != null) {
                    game.getState().setValue("attachTo:" + sourceEnchantmentCard.getId(), creature);
                    creatureController.moveCards(sourceEnchantmentCard, Zone.BATTLEFIELD, source, game);
                    return creature.addAttachment(sourceEnchantmentCard.getId(), source, game);
                }
            }
        }
        return false;
    }

    @Override
    public NecroticPlagueEffect copy() {
        return new NecroticPlagueEffect(this);
    }

}
