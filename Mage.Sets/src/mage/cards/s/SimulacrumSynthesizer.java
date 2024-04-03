package mage.cards.s;

import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.permanent.token.KarnConstructToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SimulacrumSynthesizer extends CardImpl {

    private static final FilterPermanent filter =
            new FilterArtifactPermanent("another artifact with mana value 3 or more");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new ManaValuePredicate(ComparisonType.OR_GREATER, 3));
    }

    public SimulacrumSynthesizer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        // When Substitute Synthesizer enters the battlefield, scry 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(2)));

        // Whenever another artifact with mana value 3 or more enters the battlefield under your control, create a 0/0 colorless Construct artifact creature token with "This creature gets +1/+1 for each artifact you control."
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new CreateTokenEffect(new KarnConstructToken()), filter
        ));
    }

    private SimulacrumSynthesizer(final SimulacrumSynthesizer card) {
        super(card);
    }

    @Override
    public SimulacrumSynthesizer copy() {
        return new SimulacrumSynthesizer(this);
    }
}
