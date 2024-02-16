package mage.cards.f;

import mage.abilities.effects.common.ExileTargetCardCopyAndCastEffect;
import mage.abilities.keyword.CasualtyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlawlessForgery extends CardImpl {

    private static final FilterCard filter
            = new FilterInstantOrSorceryCard("instant or sorcery card from an opponent's graveyard");

    static {
        filter.add(TargetController.OPPONENT.getOwnerPredicate());
    }

    public FlawlessForgery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        // Casualty 3
        this.addAbility(new CasualtyAbility(3));

        // Exile target instant or sorcery card from an opponent's graveyard. Copy that card. You may cast the copy without paying its mana cost.
        this.getSpellAbility().addEffect(new ExileTargetCardCopyAndCastEffect(true).setText(
                "Exile target instant or sorcery card from an opponent's graveyard. Copy that card. You may cast the copy without paying its mana cost."));
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(filter));
    }

    private FlawlessForgery(final FlawlessForgery card) {
        super(card);
    }

    @Override
    public FlawlessForgery copy() {
        return new FlawlessForgery(this);
    }
}