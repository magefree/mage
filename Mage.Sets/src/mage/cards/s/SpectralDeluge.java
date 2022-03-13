package mage.cards.s;

import mage.MageObject;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpectralDeluge extends CardImpl {

    private static final FilterPermanent filter = new FilterOpponentsCreaturePermanent();

    static {
        filter.add(SpectralDelugePredicate.instance);
    }

    public SpectralDeluge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Return each creature your opponents control with toughness X or less to its owner's hand, where X is the number of Islands you control.
        this.getSpellAbility().addEffect(new ReturnToHandFromBattlefieldAllEffect(filter).setText(
                "return each creature your opponents control with toughness X or less to its owner's hand, " +
                        "where X is the number of Islands you control"
        ));

        // Foretell {1}{U}{U}
        this.addAbility(new ForetellAbility(this, "{1}{U}{U}"));
    }

    private SpectralDeluge(final SpectralDeluge card) {
        super(card);
    }

    @Override
    public SpectralDeluge copy() {
        return new SpectralDeluge(this);
    }
}

enum SpectralDelugePredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;
    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ISLAND);

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        return input
                .getObject()
                .getToughness()
                .getValue()
                <= game
                .getBattlefield()
                .count(filter, input.getPlayerId(), input.getSource(), game);
    }
}