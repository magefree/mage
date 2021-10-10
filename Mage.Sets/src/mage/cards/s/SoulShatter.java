package mage.cards.s;

import mage.MageObject;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SoulShatter extends CardImpl {

    private static final FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent(
            "creature or planeswalker with the highest mana value " +
                    "among creatures and planeswalkers they control"
    );

    static {
        filter.add(SoulShatterPredicate.instance);
    }

    public SoulShatter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Each opponent sacrifices a creature or planeswalker with the highest converted mana cost among creatures and planeswalkers they control.
        this.getSpellAbility().addEffect(new SacrificeOpponentsEffect(filter));
    }

    private SoulShatter(final SoulShatter card) {
        super(card);
    }

    @Override
    public SoulShatter copy() {
        return new SoulShatter(this);
    }
}

enum SoulShatterPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    private static final FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent();

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        int cmc = game.getBattlefield()
                .getActivePermanents(filter, input.getPlayerId(), game)
                .stream()
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .max()
                .orElse(0);
        return input.getObject().getManaValue() >= cmc;
    }
}
