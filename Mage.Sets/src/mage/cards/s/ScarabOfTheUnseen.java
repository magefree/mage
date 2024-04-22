package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author L_J
 */
public final class ScarabOfTheUnseen extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent you own");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public ScarabOfTheUnseen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {T}, Sacrifice Scarab of the Unseen: Return all Auras attached to target permanent you own to their owners’ hands. Draw a card at the beginning of the next turn’s upkeep.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScarabOfTheUnseenEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1)), false));
        this.addAbility(ability);
    }

    private ScarabOfTheUnseen(final ScarabOfTheUnseen card) {
        super(card);
    }

    @Override
    public ScarabOfTheUnseen copy() {
        return new ScarabOfTheUnseen(this);
    }
}

class ScarabOfTheUnseenEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(SubType.AURA.getPredicate());
    }

    public ScarabOfTheUnseenEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return all Auras attached to target permanent you own to their owners' hands";
    }

    private ScarabOfTheUnseenEffect(final ScarabOfTheUnseenEffect effect) {
        super(effect);
    }

    @Override
    public ScarabOfTheUnseenEffect copy() {
        return new ScarabOfTheUnseenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
        if (controller != null && targetPermanent != null) {
            if (!targetPermanent.getAttachments().isEmpty()) {
                List<UUID> attachments = new ArrayList<>();
                attachments.addAll(targetPermanent.getAttachments());
                for (UUID attachedId : attachments) {
                    Permanent attachedPerm = game.getPermanent(attachedId);
                    if (attachedPerm != null && filter.match(attachedPerm, game)) {
                        controller.moveCards(attachedPerm, Zone.HAND, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
