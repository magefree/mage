
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class GhostlyWings extends CardImpl {

    public GhostlyWings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +1/+1 and has flying.
        Effect effect = new BoostEnchantedEffect(1, 1, Duration.WhileOnBattlefield);
        effect.setText("Enchanted creature gets +1/+1");
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA);
        effect.setText("and has flying");
        ability.addEffect(effect);
        this.addAbility(ability);
        // Discard a card: Return enchanted creature to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GhostlyWingsReturnEffect(), new DiscardCardCost()));

    }

    private GhostlyWings(final GhostlyWings card) {
        super(card);
    }

    @Override
    public GhostlyWings copy() {
        return new GhostlyWings(this);
    }
}

class GhostlyWingsReturnEffect extends OneShotEffect {

    public GhostlyWingsReturnEffect() {
        super(Outcome.ReturnToHand);
        staticText = "Return enchanted creature to its owner's hand";
    }

    public GhostlyWingsReturnEffect(final GhostlyWingsReturnEffect effect) {
        super(effect);
    }

    @Override
    public GhostlyWingsReturnEffect copy() {
        return new GhostlyWingsReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && permanent != null && permanent.getAttachedTo() != null) {
            Permanent enchantedCreature = game.getPermanent(permanent.getAttachedTo());
            if (enchantedCreature != null) {
                controller.moveCards(enchantedCreature, Zone.HAND, source, game);
            }
            return true;
        }
        return false;
    }
}
