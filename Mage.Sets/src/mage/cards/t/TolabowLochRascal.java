package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.RulebreakerAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.OtterProwessToken;

/**
 *
 * @author Grath
 */
public final class TolabowLochRascal extends CardImpl {

    private static final FilterCard filter = new FilterCard("the color identity of instant and sorcery cards in your deck can include one color of your choice not in your commander’s color identity,");

    static {
        filter.add(Predicates.or(CardType.INSTANT.getPredicate(), CardType.SORCERY.getPredicate(), SuperType.BASIC.getPredicate()));
    }

    public TolabowLochRascal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OTTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Rulebreaker -- If Tolabow, Loch Rascal is your commander, the color identity of instant and sorcery cards in
        // your deck can include one color of your choice not in your commander's color identity, and your deck can
        // have any basic land cards.
        this.addAbility(new RulebreakerAbility(filter, true).setText(
                "If Tolabow, Loch Rascal is your commander, the color identity of instant and sorcery cards in your" +
                " deck can include one color of your choice not in your commander's color identity, and your deck can" +
                " have any basic land cards."
        ));
        // Whenever you cast an instant or sorcery spell, create a 1/1 blue and red Otter creature token with prowess.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new OtterProwessToken()),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));
    }

    private TolabowLochRascal(final TolabowLochRascal card) {
        super(card);
    }

    @Override
    public TolabowLochRascal copy() {
        return new TolabowLochRascal(this);
    }
}
