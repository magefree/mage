
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class AuraFinesse extends CardImpl {

    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("Aura you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.AURA.getPredicate());
    }

    public AuraFinesse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");


        // Attach target Aura you control to target creature.
        this.getSpellAbility().addEffect(new AuraFinesseEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private AuraFinesse(final AuraFinesse card) {
        super(card);
    }

    @Override
    public AuraFinesse copy() {
        return new AuraFinesse(this);
    }
}

class AuraFinesseEffect extends OneShotEffect {

    public AuraFinesseEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Attach target Aura you control to target creature";
    }

    public AuraFinesseEffect(final AuraFinesseEffect effect) {
        super(effect);
    }

    @Override
    public AuraFinesseEffect copy() {
        return new AuraFinesseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent aura = game.getPermanent(source.getFirstTarget());
            Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
            if (aura != null && creature != null) {
                Permanent oldCreature = game.getPermanent(aura.getAttachedTo());
                if (oldCreature != null && !oldCreature.equals(creature)) {
                    Target auraTarget = aura.getSpellAbility().getTargets().get(0);
                    if (!auraTarget.canTarget(creature.getId(), game))  {
                        game.informPlayers(aura.getLogName() + " was not attched to " +creature.getLogName() + " because it's no legal target for the aura" );
                    } else if (oldCreature.removeAttachment(aura.getId(), source, game)) {
                        game.informPlayers(aura.getLogName() + " was unattached from " + oldCreature.getLogName() + " and attached to " + creature.getLogName());
                        creature.addAttachment(aura.getId(), source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
