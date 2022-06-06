package mage.cards.d;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class Disappear extends CardImpl {

    public Disappear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");
        
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // {U}: Return enchanted creature and Disappear to their owners' hands.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DisappearEffect(), new ManaCostsImpl<>("{U}")));
        
    }

    private Disappear(final Disappear card) {
        super(card);
    }

    @Override
    public Disappear copy() {
        return new Disappear(this);
    }
}

class DisappearEffect extends OneShotEffect {

    public DisappearEffect() {
        super(Outcome.ReturnToHand);
        staticText = "Return enchanted creature and {this} to their owners' hands";
    }

    public DisappearEffect(final DisappearEffect effect) {
        super(effect);
    }

    @Override
    public DisappearEffect copy() {
        return new DisappearEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent aura = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null 
                && aura != null 
                && aura.getAttachedTo() != null) {
            Permanent enchantedCreature = game.getPermanent(aura.getAttachedTo());
            controller.moveCards(aura, Zone.HAND, source, game);
            if (enchantedCreature != null) {
                controller.moveCards(enchantedCreature, Zone.HAND, source, game);
            }
            return true;
        }
        return false;
    }
}
