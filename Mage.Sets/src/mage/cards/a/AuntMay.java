package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AuntMay extends CardImpl {

    public AuntMay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Whenever another creature you control enters, you gain 1 life. If it's a Spider, put a +1/+1 counter on it.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new GainLifeEffect(1),
                StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL,
                false, SetTargetPointer.PERMANENT
        );
        ability.addEffect(new AuntMayEffect());
        this.addAbility(ability);
    }

    private AuntMay(final AuntMay card) {
        super(card);
    }

    @Override
    public AuntMay copy() {
        return new AuntMay(this);
    }
}

class AuntMayEffect extends OneShotEffect {

    AuntMayEffect() {
        super(Outcome.Benefit);
        staticText = "If it's a Spider, put a +1/+1 counter on it";
    }

    private AuntMayEffect(final AuntMayEffect effect) {
        super(effect);
    }

    @Override
    public AuntMayEffect copy() {
        return new AuntMayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(getTargetPointer().getFirst(game, source))
                .map(game::getPermanent)
                .filter(permanent -> permanent.hasSubtype(SubType.SPIDER, game))
                .filter(permanent -> permanent.addCounters(CounterType.P1P1.createInstance(), source, game))
                .isPresent();
    }
}
