
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
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
 * @author Galatolol
 */
public final class SunClasp extends CardImpl {

    public SunClasp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +1/+3.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1, 3, Duration.WhileOnBattlefield)));
        // {W}: Return enchanted creature to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SunClaspReturnEffect(), new ColoredManaCost(ColoredManaSymbol.W)));
    }

    private SunClasp(final SunClasp card) {
        super(card);
    }

    @Override
    public SunClasp copy() {
        return new SunClasp(this);
    }
}

class SunClaspReturnEffect extends OneShotEffect {

    public SunClaspReturnEffect() {
        super(Outcome.ReturnToHand);
        staticText = "Return enchanted creature to its owner's hand";
    }

    public SunClaspReturnEffect(final mage.cards.s.SunClaspReturnEffect effect) {
        super(effect);
    }

    @Override
    public mage.cards.s.SunClaspReturnEffect copy() {
        return new mage.cards.s.SunClaspReturnEffect(this);
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
