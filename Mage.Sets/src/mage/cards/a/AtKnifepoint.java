package mage.cards.a;

import mage.abilities.common.CommittedCrimeTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.OutlawPredicate;
import mage.game.permanent.token.MercenaryToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AtKnifepoint extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(OutlawPredicate.instance);
    }

    public AtKnifepoint(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{R}");

        // As long as it's your turn, outlaws you control have first strike.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter),
                MyTurnCondition.instance, "as long as it's your turn, outlaws you control have first strike"
        )));

        // Whenever you commit a crime, create a 1/1 red Mercenary creature token with "{T}: Target creature you control gets +1/+0 until end of turn. Activate only as a sorcery." This ability triggers only once each turn.
        this.addAbility(new CommittedCrimeTriggeredAbility(new CreateTokenEffect(new MercenaryToken())).setTriggersOnceEachTurn(true));
    }

    private AtKnifepoint(final AtKnifepoint card) {
        super(card);
    }

    @Override
    public AtKnifepoint copy() {
        return new AtKnifepoint(this);
    }
}
