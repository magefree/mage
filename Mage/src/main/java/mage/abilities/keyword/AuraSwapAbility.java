
package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.card.AuraCardCanAttachToPermanentId;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author Mael
 */
public class AuraSwapAbility extends ActivatedAbilityImpl {

    public AuraSwapAbility(ManaCost manaCost) {
        super(Zone.BATTLEFIELD, new AuraSwapEffect(), manaCost);
    }

    public AuraSwapAbility(final AuraSwapAbility ability) {
        super(ability);
    }

    @Override
    public AuraSwapAbility copy() {
        return new AuraSwapAbility(this);
    }

    @Override
    public String getRule() {
        return new StringBuilder("Aura swap ").append(getManaCostsToPay().getText()).append(" <i>(")
                .append(getManaCostsToPay().getText())
                .append(": Exchange this Aura with an Aura card in your hand.)</i>").toString();
    }
}

class AuraSwapEffect extends OneShotEffect {

    AuraSwapEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Exchange this Aura with an Aura card in your hand.";
    }

    AuraSwapEffect(final AuraSwapEffect effect) {
        super(effect);
    }

    @Override
    public AuraSwapEffect copy() {
        return new AuraSwapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCard filterCardToCheck = new FilterCard();
        filterCardToCheck.add(SubType.AURA.getPredicate());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent auraSourcePermanent = game.getPermanent(source.getSourceId());
            if (auraSourcePermanent != null
                    && auraSourcePermanent.hasSubtype(SubType.AURA, game)
                    && auraSourcePermanent.isOwnedBy(source.getControllerId())) {
                Permanent enchantedPermanent = game.getPermanent(auraSourcePermanent.getAttachedTo());
                filterCardToCheck.add(new AuraCardCanAttachToPermanentId(enchantedPermanent.getId()));
                TargetCardInHand target = new TargetCardInHand(filterCardToCheck);
                if (controller.choose(Outcome.PutCardInPlay, target, source, game)) {
                    Card auraInHand = game.getCard(target.getFirstTarget());
                    if (auraInHand != null) {
                        game.getState().setValue("attachTo:" + auraInHand.getId(), enchantedPermanent);
                        controller.moveCards(auraInHand, Zone.BATTLEFIELD, source, game);
                        enchantedPermanent.addAttachment(auraInHand.getId(), source, game);
                        game.informPlayers(controller.getLogName() + " put " + auraInHand.getLogName() + " on the battlefield attached to " + enchantedPermanent.getLogName() + '.');
                        enchantedPermanent.removeAttachment(auraSourcePermanent.getId(), source, game);
                        return controller.moveCards(auraSourcePermanent, Zone.HAND, source, game);
                    }
                }
            }
        }
        return false;
    }
}
