package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.condition.common.AddendumCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.ShuffleHandGraveyardAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmergencyPowers extends CardImpl {

    public EmergencyPowers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{W}{U}");

        // Each player shuffles their hand and graveyard into their library, then draws seven cards. Exile Emergency Powers.
        this.getSpellAbility().addEffect(new ShuffleHandGraveyardAllEffect());
        this.getSpellAbility().addEffect(new DrawCardAllEffect(7).setText(", then draws seven cards"));

        // Addendum — If you cast this spell during your main phase, you may put a permanent card with converted mana cost 7 or less from your hand onto the battlefield.
        this.getSpellAbility().addEffect(new EmergencyPowersEffect());
    }

    private EmergencyPowers(final EmergencyPowers card) {
        super(card);
    }

    @Override
    public EmergencyPowers copy() {
        return new EmergencyPowers(this);
    }
}

class EmergencyPowersEffect extends OneShotEffect {

    public static final FilterPermanentCard filter
            = new FilterPermanentCard("a permanent card with mana value 7 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 8));
    }

    EmergencyPowersEffect() {
        super(Outcome.Benefit);
        staticText = "Exile {this}.<br><i>Addendum</i> &mdash; If you cast this spell during your main phase, " +
                "you may put a permanent card with mana value 7 or less from your hand onto the battlefield.";
    }

    private EmergencyPowersEffect(final EmergencyPowersEffect effect) {
        super(effect);
    }

    @Override
    public EmergencyPowersEffect copy() {
        return new EmergencyPowersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (AddendumCondition.instance.apply(game, source)) {
            new PutCardFromHandOntoBattlefieldEffect(filter).apply(game, source);
        }
        return new ExileSpellEffect().apply(game, source);
    }
}
// I am the senate!
