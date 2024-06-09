package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.OutlawPredicate;
import mage.game.permanent.token.MercenaryToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RakishCrew extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("an outlaw you control");

    static {
        filter.add(OutlawPredicate.instance);
    }

    public RakishCrew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // When Rakish Crew enters the battlefield, create a 1/1 red Mercenary creature token with "{T}: Target creature you control gets +1/+0 until end of turn. Activate only as a sorcery."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MercenaryToken())));

        // Whenever an outlaw you control dies, each opponent loses 1 life and you gain 1 life.
        Ability ability = new DiesCreatureTriggeredAbility(new LoseLifeOpponentsEffect(1), false, filter);
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private RakishCrew(final RakishCrew card) {
        super(card);
    }

    @Override
    public RakishCrew copy() {
        return new RakishCrew(this);
    }
}
