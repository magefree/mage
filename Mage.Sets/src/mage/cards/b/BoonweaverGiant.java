
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
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
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

    public BoonweaverGiant(final BoonweaverGiant card) {
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
        this.staticText = "you may search your graveyard, hand, and/or library for an Aura card and put it onto the battlefield attached to {this}. If you search your library this way, shuffle it.";
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
        if (controller == null) {
            return false;
        }

        FilterCard filter = new FilterCard("Aura card");
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
        filter.add(new SubtypePredicate(SubType.AURA));

        Card card = null;
        Zone zone = null;
        if (controller.chooseUse(Outcome.Neutral, "Search your graveyard for an Aura card?", source, game)) {
            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(filter);
            if (controller.choose(Outcome.PutCardInPlay, controller.getGraveyard(), target, game)) {
                card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    zone = Zone.GRAVEYARD;
                }
            }
        }
        if (card == null && controller.chooseUse(Outcome.Neutral, "Search your Hand for an Aura card?", source, game)) {
            TargetCardInHand target = new TargetCardInHand(filter);
            if (controller.choose(Outcome.PutCardInPlay, controller.getHand(), target, game)) {
                card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    zone = Zone.HAND;
                }
            }
        }
        if (card == null) {
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            if (controller.searchLibrary(target, game)) {
                card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    zone = Zone.LIBRARY;
                }
            }
            controller.shuffleLibrary(source, game);
        }
        // aura card found - attach it
        if (card != null) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                game.getState().setValue("attachTo:" + card.getId(), permanent);
            }
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            if (permanent != null) {
                return permanent.addAttachment(card.getId(), game);
            }
        }
        return true;
    }
}
