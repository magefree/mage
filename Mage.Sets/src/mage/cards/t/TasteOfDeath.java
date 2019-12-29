package mage.cards.t;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TasteOfDeath extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("creatures");

    public TasteOfDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // Each player sacrifices three creatures. You create three Food tokens.
        this.getSpellAbility().addEffect(new SacrificeAllEffect(3, filter));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new FoodToken(), 3).concatBy("You"));
    }

    private TasteOfDeath(final TasteOfDeath card) {
        super(card);
    }

    @Override
    public TasteOfDeath copy() {
        return new TasteOfDeath(this);
    }
}
