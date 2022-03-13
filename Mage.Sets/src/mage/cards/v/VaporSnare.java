
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.ControlEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class VaporSnare extends CardImpl {

    public VaporSnare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{U}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.GainControl));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // You control enchanted creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ControlEnchantedEffect()));

        // At the beginning of your upkeep, sacrifice Vapor Snare unless you return a land you control to its owner's hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new VaporSnareEffect(), TargetController.YOU, false));
    }

    private VaporSnare(final VaporSnare card) {
        super(card);
    }

    @Override
    public VaporSnare copy() {
        return new VaporSnare(this);
    }
}

class VaporSnareEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();
    private static final String effectText = "sacrifice {this} unless you return a land you control to its owner's hand";

    VaporSnareEffect( ) {
        super(Outcome.Sacrifice);
        staticText = effectText;
    }

    VaporSnareEffect(VaporSnareEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean targetChosen = false;
        Player controller = game.getPlayer(source.getControllerId());
        TargetPermanent target = new TargetPermanent(1, 1, filter, false);

        if (controller != null 
                && target.canChoose(controller.getId(), source, game)) {
            controller.choose(Outcome.Sacrifice, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());

            if ( permanent != null ) {
                targetChosen = true;
                controller.moveCards(permanent, Zone.HAND, source, game);
            }
        }

        if ( !targetChosen ) {
            new SacrificeSourceEffect().apply(game, source);
        }

        return false;
    }

    @Override
    public VaporSnareEffect copy() {
        return new VaporSnareEffect(this);
    }
}
