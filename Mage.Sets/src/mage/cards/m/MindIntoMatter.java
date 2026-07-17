package mage.cards.m;

import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MindIntoMatter extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("a permanent card with mana value X or less");

    static {
        filter.add(MindIntoMatterPredicate.instance);
    }

    public MindIntoMatter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}{U}");

        // Draw X cards. Then you may put a permanent card with mana value X or less from your hand onto the battlefield tapped.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(GetXValue.instance));
        this.getSpellAbility().addEffect(new PutCardFromHandOntoBattlefieldEffect(
                filter, false, true
        ).concatBy("Then"));
    }

    private MindIntoMatter(final MindIntoMatter card) {
        super(card);
    }

    @Override
    public MindIntoMatter copy() {
        return new MindIntoMatter(this);
    }
}

enum MindIntoMatterPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return input.getObject().getManaValue() <= GetXValue.instance.calculate(game, input.getSource(), null);
    }
}
