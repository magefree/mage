package mage.cards.f;

import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import java.util.UUID;

/**
 * @author nick.myers
 */
public final class ForcedMarch extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with converted mana cost X or less");

    static {
        filter.add(ForcedMarchPredicate.instance);
    }

    public ForcedMarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{B}{B}");

        // Destroy all creatures with converted mana cost X or less
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    private ForcedMarch(final ForcedMarch card) {
        super(card);
    }

    @Override
    public ForcedMarch copy() {
        return new ForcedMarch(this);
    }
}

enum ForcedMarchPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return input.getObject().getManaValue() <= GetXValue.instance.calculate(game, input.getSource(), null);
    }
}
