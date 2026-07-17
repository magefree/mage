package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PucasCovenant extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("a creature you control with a counter on it");
    private static final FilterCard filter2 = new FilterPermanentCard("another target permanent card " +
            "with mana value less than or equal to the number of counters on that creature from your graveyard");

    static {
        filter.add(CounterAnyPredicate.instance);
        filter2.add(PucasCovenantPredicate.instance);
    }

    public PucasCovenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Whenever a creature you control with a counter on it dies, you may return another target permanent card with mana value less than or equal to the number of counters on that creature from your graveyard to your hand. Do this only once each turn.
        Ability ability = new DiesCreatureTriggeredAbility(
                new ReturnFromGraveyardToHandTargetEffect(), true, filter
        ).setDoOnlyOnceEachTurn(true);
        ability.addTarget(new TargetCardInYourGraveyard(filter2));
        this.addAbility(ability);
    }

    private PucasCovenant(final PucasCovenant card) {
        super(card);
    }

    @Override
    public PucasCovenant copy() {
        return new PucasCovenant(this);
    }
}

enum PucasCovenantPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return CardUtil
                .getEffectValueFromAbility(input.getSource(), "creatureDied", Permanent.class)
                .filter(permanent -> !input.getObject().getId().equals(permanent.getId()))
                .map(permanent -> permanent.getCounters(game).getTotalCount())
                .filter(count -> input.getObject().getManaValue() <= count)
                .isPresent();
    }
}
