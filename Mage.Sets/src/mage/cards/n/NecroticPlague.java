
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
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature has "At the beginning of your upkeep, sacrifice this creature."
        // When enchanted creature dies, its controller chooses target creature one of their opponents controls. Return Necrotic Plague from its owner's graveyard to the battlefield attached to that creature.
        ability = new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceEffect(), TargetController.YOU, false);
        Effect effect = new GainAbilityAttachedEffect(ability, AttachmentType.AURA, Duration.WhileOnBattlefield);
        effect.setText("Enchanted creature has \"At the beginning of your upkeep, sacrifice this creature.\"");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        ability = new DiesAttachedTriggeredAbility(new NecroticPlagueEffect(), "enchanted creature", false);
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
        Player sourceController = game.getPlayer(source.getControllerId());
        Card sourceEnchantmentCard = game.getCard(source.getSourceId());
        if (attachedTo != null && sourceController != null && sourceEnchantmentCard != null) {
            Player creatureController = game.getPlayer(attachedTo.getControllerId());
            if (creatureController != null) {
                TargetOpponentsCreaturePermanent target = new TargetOpponentsCreaturePermanent();
                if (target.canChoose(sourceEnchantmentCard.getId(), creatureController.getId(), game)) {
                    creatureController.chooseTarget(outcome, target, source, game);
                    Permanent creature = game.getPermanent(target.getFirstTarget());
                    if (creature != null) {
                        game.getState().setValue("attachTo:" + sourceEnchantmentCard.getId(), creature);
                        sourceController.moveCards(sourceEnchantmentCard, Zone.BATTLEFIELD, source, game);
                        return creature.addAttachment(sourceEnchantmentCard.getId(), source, game);
                    }
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
