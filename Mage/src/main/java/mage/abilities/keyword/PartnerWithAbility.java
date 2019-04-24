
package mage.abilities.keyword;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public class PartnerWithAbility extends EntersBattlefieldTriggeredAbility {

    private final String partnerName;
    private final String shortName;
    private final boolean hasReminderText;

    public PartnerWithAbility(String partnerName) {
        this(partnerName, false);
    }

    public PartnerWithAbility(String partnerName, boolean isLegendary) {
        this(partnerName, isLegendary, true);
    }

    public PartnerWithAbility(String partnerName, boolean isLegendary, boolean hasReminderText) {
        super(new PartnersWithSearchEffect(partnerName), false);
        this.addTarget(new TargetPlayer());
        this.partnerName = partnerName;
        this.hasReminderText = hasReminderText;
        if (isLegendary) {
            this.shortName = shortenName(partnerName);
        } else {
            this.shortName = partnerName;
        }
    }

    public PartnerWithAbility(final PartnerWithAbility ability) {
        super(ability);
        this.partnerName = ability.partnerName;
        this.shortName = ability.shortName;
        this.hasReminderText = ability.hasReminderText;
    }

    @Override
    public PartnerWithAbility copy() {
        return new PartnerWithAbility(this);
    }

    @Override
    public String getRule() {
        if (hasReminderText) {
            return "Partner with " + partnerName
                    + " <i>(When this creature enters the battlefield, target player may put " + shortName
                    + " into their hand from their library, then shuffle.)</i>";
        } else {
            return "Partner with " + partnerName;
        }
    }

    public String getPartnerName() {
        return partnerName;
    }

    public static String shortenName(String st) {
        StringBuilder sb = new StringBuilder();
        for (char s : st.toCharArray()) {
            if (s == ' ' || s == ',') {
                break;
            } else {
                sb.append(s);
            }
        }
        return sb.toString();
    }
}

class PartnersWithSearchEffect extends OneShotEffect {

    private final String partnerName;

    public PartnersWithSearchEffect(String partnerName) {
        super(Outcome.DrawCard);
        this.partnerName = partnerName;
        this.staticText = "";
    }

    public PartnersWithSearchEffect(final PartnersWithSearchEffect effect) {
        super(effect);
        this.partnerName = effect.partnerName;
    }

    @Override
    public PartnersWithSearchEffect copy() {
        return new PartnersWithSearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player player = game.getPlayer(source.getFirstTarget());
            if (player != null) {
                FilterCard filter = new FilterCard("card named " + partnerName);
                filter.add(new NamePredicate(partnerName));
                TargetCardInLibrary target = new TargetCardInLibrary(filter);
                if (player.chooseUse(Outcome.Benefit, "Search your library for a card named " + partnerName + " and put it into your hand?", source, game)) {
                    player.searchLibrary(target, game);
                    for (UUID cardId : target.getTargets()) {
                        Card card = player.getLibrary().getCard(cardId, game);
                        if (card != null) {
                            player.revealCards(source, new CardsImpl(card), game);
                            player.moveCards(card, Zone.HAND, source, game);
                        }
                    }
                    player.shuffleLibrary(source, game);
                }
            }
            // prevent undo
            controller.resetStoredBookmark(game);
            return true;
        }
        return false;
    }
}
