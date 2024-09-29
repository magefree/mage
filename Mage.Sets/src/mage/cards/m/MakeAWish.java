package mage.cards.m;

import mage.abilities.effects.common.ReturnFromGraveyardAtRandomEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author North
 */
public final class MakeAWish extends CardImpl {

    public MakeAWish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Return two cards at random from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardAtRandomEffect(StaticFilters.FILTER_CARD_CARDS, Zone.HAND, 2));
    }

    private MakeAWish(final MakeAWish card) {
        super(card);
    }

    @Override
    public MakeAWish copy() {
        return new MakeAWish(this);
    }
}
