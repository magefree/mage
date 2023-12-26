package mage.cards.s;

import mage.abilities.effects.common.ReturnFromGraveyardAtRandomEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class SurrealMemoir extends CardImpl {

    private static final FilterCard filter = new FilterCard("an instant card");
    static {
        filter.add(CardType.INSTANT.getPredicate());
    }

    public SurrealMemoir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Return an instant card at random from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardAtRandomEffect(filter, Zone.HAND));
        this.addAbility(new ReboundAbility());
    }

    private SurrealMemoir(final SurrealMemoir card) {
        super(card);
    }

    @Override
    public SurrealMemoir copy() {
        return new SurrealMemoir(this);
    }
}
