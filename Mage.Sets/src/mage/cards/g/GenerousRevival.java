package mage.cards.g;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author muz
 */
public final class GenerousRevival extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    public GenerousRevival(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // Return target creature card with mana value 3 or less from your graveyard to the battlefield with an additional +1/+1 counter on it.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(true, CounterType.P1P1.createInstance()));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));

        // Flashback {4}{W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{4}{W}")));
    }

    private GenerousRevival(final GenerousRevival card) {
        super(card);
    }

    @Override
    public GenerousRevival copy() {
        return new GenerousRevival(this);
    }
}
