package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LocthwainLancer extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(
            SubType.KNIGHT, "a nontoken Knight you control"
    );

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public LocthwainLancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever a nontoken Knight you control dies, each opponent loses 1 life and you draw a card.
        Ability ability = new DiesCreatureTriggeredAbility(new LoseLifeOpponentsEffect(1), false, filter);
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and you"));
        this.addAbility(ability);
    }

    private LocthwainLancer(final LocthwainLancer card) {
        super(card);
    }

    @Override
    public LocthwainLancer copy() {
        return new LocthwainLancer(this);
    }
}
