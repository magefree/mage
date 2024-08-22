package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.BloodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SanguineBrushstroke extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.BLOOD, "a Blood token");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public SanguineBrushstroke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");

        // When Sanguine Brushstroke enters, create a Blood token and conjure a card named Blood Artist onto the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new BloodToken()));
        ability.addEffect(new ConjureCardEffect("Blood Artist", Zone.BATTLEFIELD, 1).concatBy("and"));
        this.addAbility(ability);

        // Whenever you sacrifice a Blood token, each opponent loses 1 life and you gain 1 life.
        ability = new SacrificePermanentTriggeredAbility(new LoseLifeOpponentsEffect(1), filter);
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private SanguineBrushstroke(final SanguineBrushstroke card) {
        super(card);
    }

    @Override
    public SanguineBrushstroke copy() {
        return new SanguineBrushstroke(this);
    }
}
