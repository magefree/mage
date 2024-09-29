package mage.cards.s;

import mage.abilities.condition.common.DescendCondition;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.PermanentPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SquirmingEmergence extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard(
            "nonland permanent card in your graveyard with mana value less than or equal to the number of permanent cards in your graveyard"
    );

    static {
        filter.add(PermanentPredicate.instance);
        filter.add(SquirmingEmergencePredicate.instance);
    }

    public SquirmingEmergence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{G}");

        // Fathomless descent -- Return to the battlefield target nonland permanent card in your graveyard with mana value less than or equal to the number of permanent cards in your graveyard.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setText("return to the battlefield target nonland permanent card in your graveyard "
                        + "with mana value less than or equal to the number of permanent cards in your graveyard"));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
        this.getSpellAbility().setAbilityWord(AbilityWord.FATHOMLESS_DESCENT);
        this.getSpellAbility().addHint(DescendCondition.getHint());
    }

    private SquirmingEmergence(final SquirmingEmergence card) {
        super(card);
    }

    @Override
    public SquirmingEmergence copy() {
        return new SquirmingEmergence(this);
    }
}

enum SquirmingEmergencePredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        Player player = game.getPlayer(input.getSource().getControllerId());
        return player != null
                && player.getGraveyard().count(StaticFilters.FILTER_CARD_PERMANENT, game) >= input.getObject().getManaValue();
    }
}
