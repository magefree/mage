
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author vereena42
 */
public final class DeathWatch extends CardImpl {

    public DeathWatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");
        
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When enchanted creature dies, its controller loses life equal to its power and you gain life equal to its toughness.
		this.addAbility( new DiesAttachedTriggeredAbility(new DeathWatchEffect(), "enchanted creature"));
    }

    private DeathWatch(final DeathWatch card) {
        super(card);
    }

    @Override
    public DeathWatch copy() {
        return new DeathWatch(this);
    }

    static class DeathWatchEffect extends OneShotEffect {

        public DeathWatchEffect() {
            super(Outcome.LoseLife);
            staticText = "that creature's controller loses life equal to its power and you gain life equal to its toughness.";
        }

        public DeathWatchEffect(DeathWatchEffect copy) {
            super(copy);
        }

        @Override
        public DeathWatchEffect copy() {
            return new DeathWatchEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent creature = (Permanent) getValue("attachedTo");
            if(creature != null){
                Player opponent = game.getPlayer(creature.getOwnerId());
                if (opponent != null) {
                    Player controller = game.getPlayer(source.getControllerId());
                    if (controller != null) {
                        controller.gainLife(creature.getToughness().getValue(), game, source);
                        opponent.loseLife(creature.getPower().getValue(), game, source, false);
                        return true;
                    }
                }
            }
            return false;
        }

    }
}
