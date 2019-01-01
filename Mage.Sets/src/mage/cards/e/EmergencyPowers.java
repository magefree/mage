package mage.cards.e;

import mage.abilities.condition.common.MyMainPhaseCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.ShuffleHandGraveyardAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmergencyPowers extends CardImpl {

    public static final FilterPermanentCard filter
            = new FilterPermanentCard("a permanent card with converted mana cost 7 or less");

    static {
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 8));
    }

    public EmergencyPowers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{W}{U}");

        // Each player shuffles their hand and graveyard into their library, then draws seven cards. Exile Emergency Powers.
        this.getSpellAbility().addEffect(new ShuffleHandGraveyardAllEffect());
        this.getSpellAbility().addEffect(new DrawCardAllEffect(7).setText(", then draws seven cards"));
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());

        // Addendum — If you cast this spell during your main phase, you may put a permanent card with converted mana cost 7 or less from your hand onto the battlefield.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new PutCardFromHandOntoBattlefieldEffect(filter),
                MyMainPhaseCondition.instance,
                "<br><i>Addendum</i> &mdash; " +
                        "If you cast this spell during your main phase, " +
                        "you may put a permanent card with converted mana cost 7 or less " +
                        "from your hand onto the battlefield."
        ));
    }

    private EmergencyPowers(final EmergencyPowers card) {
        super(card);
    }

    @Override
    public EmergencyPowers copy() {
        return new EmergencyPowers(this);
    }
}
// I am the senate!
