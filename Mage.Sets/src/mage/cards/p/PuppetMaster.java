package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author L_J
 */
public final class PuppetMaster extends CardImpl {

    public PuppetMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{U}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // When enchanted creature dies, return that card to its owner's hand. If that card is returned to its owner’s hand this way, you may pay {U}{U}{U}. If you do, return Puppet Master to its owner’s hand.
        this.addAbility(new DiesAttachedTriggeredAbility(new PuppetMasterEffect(), "enchanted creature"));
    }

    private PuppetMaster(final PuppetMaster card) {
        super(card);
    }

    @Override
    public PuppetMaster copy() {
        return new PuppetMaster(this);
    }
}

class PuppetMasterEffect extends OneShotEffect {

    PuppetMasterEffect() {
        super(Outcome.ReturnToHand);
        staticText = "return that card to its owner's hand. If that card is returned to its owner's hand this way, " +
                "you may pay {U}{U}{U}. If you do, return {this} to its owner's hand";
    }

    private PuppetMasterEffect(final PuppetMasterEffect effect) {
        super(effect);
    }

    @Override
    public PuppetMasterEffect copy() {
        return new PuppetMasterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = (Permanent) getValue("attachedTo");
        if (controller == null || permanent == null) {
            return false;
        }
        Card card = permanent.getMainCard();
        if (card == null || card.getZoneChangeCounter(game) != permanent.getZoneChangeCounter(game) + 1) {
            return false;
        }
        controller.moveCards(card, Zone.HAND, source, game);
        if (game.getState().getZone(card.getId()) != Zone.HAND) {
            return false;
        }
        card = game.getCard(source.getSourceId());
        if (card == null || card.getZoneChangeCounter(game) != source.getSourceObjectZoneChangeCounter() + 1) {
            return false;
        }
        Cost cost = new ManaCostsImpl<>("{U}{U}{U}");
        if (!controller.chooseUse(Outcome.Neutral, "Pay " + cost.getText()
                + " to return " + card.getLogName() + " to its owner's hand?", source, game)
                || !cost.pay(source, game, source, controller.getId(), false, null)) {
            return true;
        }
        controller.moveCards(card, Zone.HAND, source, game);
        return true;
    }
}
