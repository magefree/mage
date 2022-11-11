
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class BanewaspAffliction extends CardImpl {

    public BanewaspAffliction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When enchanted creature dies, that creature's controller loses life equal to its toughness.
        this.addAbility( new DiesAttachedTriggeredAbility(new BanewaspAfflictionLoseLifeEffect(), "enchanted creature"));
    }

    private BanewaspAffliction(final BanewaspAffliction card) {
        super(card);
    }

    @Override
    public BanewaspAffliction copy() {
        return new BanewaspAffliction(this);
    }
}


class BanewaspAfflictionLoseLifeEffect extends OneShotEffect {

    public BanewaspAfflictionLoseLifeEffect() {
        super(Outcome.LoseLife);
        this.staticText = "that creature's controller loses life equal to its toughness";
    }

    public BanewaspAfflictionLoseLifeEffect(BanewaspAfflictionLoseLifeEffect copy) {
        super(copy);
    }


    @Override
    public BanewaspAfflictionLoseLifeEffect copy() {
        return new BanewaspAfflictionLoseLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = (Permanent) getValue("attachedTo");
        if(creature != null){
            Player player = game.getPlayer(creature.getOwnerId());
            if (player != null) {
                player.loseLife(creature.getToughness().getValue(), game, source, false);
                return true;
            }
        }
        return false;
    }
}
