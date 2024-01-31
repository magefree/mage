package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

/**
 *
 * @author jimga150
 */
public final class ArchdruidsCharm extends CardImpl {

    // Based on Aerial Surveyor and SearchLibraryPutInHandOrOnBattlefieldEffect
    private static final FilterCard filter = new FilterCard("creature or land");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.LAND.getPredicate()));
    }

    public ArchdruidsCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{G}{G}");
        

        // Choose one --
        // * Search your library for a creature or land card and reveal it. Put it onto the battlefield tapped if it's a land card. Otherwise, put it into your hand. Then shuffle.
        // * Put a +1/+1 counter on target creature you control. It deals damage equal to its power to target creature you don't control.
        // * Exile target artifact or enchantment.
        this.getSpellAbility().addEffect(new ArchdruidsCharmMode1Effect(new TargetCardInLibrary(filter), true, new FilterLandCard()));
    }

    private ArchdruidsCharm(final ArchdruidsCharm card) {
        super(card);
    }

    @Override
    public ArchdruidsCharm copy() {
        return new ArchdruidsCharm(this);
    }
}

class ArchdruidsCharmMode1Effect extends SearchEffect {

    private final boolean revealCards;
    private final FilterCard putOnBattlefieldFilter;

    public ArchdruidsCharmMode1Effect(TargetCardInLibrary target, boolean revealCards, FilterCard putOnBattlefieldFilter) {
        super(target, Outcome.DrawCard);
        this.revealCards = revealCards;
        this.putOnBattlefieldFilter = putOnBattlefieldFilter;
        setText();
    }

    protected ArchdruidsCharmMode1Effect(final ArchdruidsCharmMode1Effect effect) {
        super(effect);
        this.revealCards = effect.revealCards;
        this.putOnBattlefieldFilter = effect.putOnBattlefieldFilter;
    }

    @Override
    public ArchdruidsCharmMode1Effect copy() {
        return new ArchdruidsCharmMode1Effect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        target.clearChosen();

        boolean searchSuccessful = false;

        if (controller.searchLibrary(target, source, game)) {
            if (!target.getTargets().isEmpty()) {
                Cards cards = new CardsImpl();
                for (UUID cardId : target.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card == null) {
                        continue;
                    }
                    if (putOnBattlefieldFilter.match(card, game)) {
                        controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
                    } else {
                        controller.moveCards(card, Zone.HAND, source, game);
                    }
                    cards.add(card);
                }
                if (revealCards) {
                    String name = "Reveal";
                    Card sourceCard = game.getCard(source.getSourceId());
                    if (sourceCard != null) {
                        name = sourceCard.getIdName();
                    }
                    controller.revealCards(name, cards, game);
                }
            }
            searchSuccessful = true;
        }
        controller.shuffleLibrary(source, game);
        return searchSuccessful;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("Search your library for ");
        if (target.getNumberOfTargets() == 0 && target.getMaxNumberOfTargets() > 0) {
            sb.append("up to ").append(CardUtil.numberToText(target.getMaxNumberOfTargets())).append(' ');
            sb.append(target.getTargetName()).append(revealCards ? " and reveal them." : ".");
        } else {
            sb.append("a ").append(target.getTargetName()).append(revealCards ? " and reveal it." : ".");
        }
        if (putOnBattlefieldFilter != null) {
            sb.append(" Put it onto the battlefield tapped if it's a ");
            sb.append(putOnBattlefieldFilter.getMessage());
            sb.append(". Otherwise, put it into your hand.");
        }
        sb.append(" Then shuffle.");
        staticText = sb.toString();
    }

}

