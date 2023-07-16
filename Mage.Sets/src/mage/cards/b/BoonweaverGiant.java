
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.card.AuraCardCanAttachToPermanentId;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class BoonweaverGiant extends CardImpl {

    public BoonweaverGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{W}");
        this.subtype.add(SubType.GIANT, SubType.MONK);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Boonweaver Giant enters the battlefield, you may search your graveyard, hand,
        // and/or library for an Aura card and put it onto the battlefield attached to Boonweaver Giant.
        // If you search your library this way, shuffle it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BoonweaverGiantEffect(), true));
    }

    private BoonweaverGiant(final BoonweaverGiant card) {
        super(card);
    }

    @Override
    public BoonweaverGiant copy() {
        return new BoonweaverGiant(this);
    }
}

class BoonweaverGiantEffect extends OneShotEffect {

    public BoonweaverGiantEffect() {
        super(Outcome.UnboostCreature);
        this.staticText = "you may search your graveyard, hand, and/or library for an Aura card and put it onto the battlefield attached to {this}. " +
                "If you search your library this way, shuffle.";
    }

    public BoonweaverGiantEffect(final BoonweaverGiantEffect effect) {
        super(effect);
    }

    @Override
    public BoonweaverGiantEffect copy() {
        return new BoonweaverGiantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) { return false; }

        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        UUID sourcePermanentId = sourcePermanent == null ? null : sourcePermanent.getId();
        FilterCard filter = new FilterCard("Aura card");
        filter.add(SubType.AURA.getPredicate());
        filter.add(new AuraCardCanAttachToPermanentId(sourcePermanentId));

        Card card = null;

        // Choose card from graveyard
        if (controller.chooseUse(Outcome.Neutral, "Search your graveyard for an Aura card?", source, game)) {
            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(filter);
            if (controller.choose(Outcome.PutCardInPlay, controller.getGraveyard(), target, source, game)) {
                card = game.getCard(target.getFirstTarget());
            }
        }

        // Choose card from your hand
        if (card == null && controller.chooseUse(Outcome.Neutral, "Search your Hand for an Aura card?", source, game)) {
            TargetCardInHand target = new TargetCardInHand(filter);
            if (controller.choose(Outcome.PutCardInPlay, controller.getHand(), target, source, game)) {
                card = game.getCard(target.getFirstTarget());
            }
        }

        // Choose a card from your library
        if (card == null) {
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            if (controller.searchLibrary(target, source, game)) {
                card = game.getCard(target.getFirstTarget());
            }
            controller.shuffleLibrary(source, game);
        }

        // Aura card found - attach it
        if (card != null) {
            if (sourcePermanent != null) {
                game.getState().setValue("attachTo:" + card.getId(), sourcePermanent);
            }
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            if (sourcePermanent != null) {
                return sourcePermanent.addAttachment(card.getId(), source, game);
            }
        }
        return true;
    }
}
