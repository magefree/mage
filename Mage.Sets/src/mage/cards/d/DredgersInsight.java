package mage.cards.d;

import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DredgersInsight extends CardImpl {

    private static final FilterCard filter = new FilterCard("artifact and/or creature cards");
    private static final FilterCard filter2 = new FilterCard("an artifact, creature, or land card");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
        filter2.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public DredgersInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // Whenever one or more artifact and/or creature cards leave your graveyard, you gain 1 life.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(new GainLifeEffect(1), filter));

        // When this enchantment enters, mill four cards. You may put an artifact, creature, or land card from among the milled cards into your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillThenPutInHandEffect(4, filter2)));
    }

    private DredgersInsight(final DredgersInsight card) {
        super(card);
    }

    @Override
    public DredgersInsight copy() {
        return new DredgersInsight(this);
    }
}
