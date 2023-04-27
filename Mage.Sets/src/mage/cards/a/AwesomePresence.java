package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.PayCostToAttackBlockEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class AwesomePresence extends CardImpl {

    public AwesomePresence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature can't be blocked unless defending player pays {3} for each creature they control that's blocking it.
        this.addAbility(new SimpleStaticAbility(new AwesomePresenceRestrictionEffect(new ManaCostsImpl<>("{3}"))));

    }

    private AwesomePresence(final AwesomePresence card) {
        super(card);
    }

    @Override
    public AwesomePresence copy() {
        return new AwesomePresence(this);
    }
}

class AwesomePresenceRestrictionEffect extends PayCostToAttackBlockEffectImpl {

    public AwesomePresenceRestrictionEffect(ManaCosts manaCosts) {
        super(Duration.WhileOnBattlefield, Outcome.Neutral, RestrictType.BLOCK, manaCosts);
        staticText = "Enchanted creature"
                + " can't be blocked "
                + "unless defending player pays "
                + (manaCosts == null ? "" : manaCosts.getText()
                + " for each creature they control that's blocking it");
    }

    public AwesomePresenceRestrictionEffect(AwesomePresenceRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent blockingCreature = game.getPermanent(event.getSourceId());
        Permanent enchantedAttackingCreature = game.getPermanent(event.getTargetId());
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (blockingCreature != null
                && enchantedAttackingCreature != null
                && enchantment != null
                && enchantment.isAttachedTo(enchantedAttackingCreature.getId())) {
            Player defendingPlayer = game.getPlayer(blockingCreature.getControllerId());
            if (defendingPlayer != null) {
                return !manaCosts.canPay(source, source, defendingPlayer.getId(), game)
                        || !manaCosts.pay(source, game, source, defendingPlayer.getId(), false);
            }
        }
        return true;
    }

    @Override
    public AwesomePresenceRestrictionEffect copy() {
        return new AwesomePresenceRestrictionEffect(this);
    }
}
