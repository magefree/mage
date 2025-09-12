package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AntiVenomHorrifyingHealer extends CardImpl {

    public AntiVenomHorrifyingHealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}{W}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SYMBIOTE);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When Anti-Venom enters, if he was cast, return target creature card from your graveyard to the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect())
                .withInterveningIf(CastFromEverywhereSourceCondition.instance);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);

        // If damage would be dealt to Anti-Venom, prevent that damage and put that many +1/+1 counters on him.
        this.addAbility(new SimpleStaticAbility(new AntiVenomHorrifyingHealerEffect()));
    }

    private AntiVenomHorrifyingHealer(final AntiVenomHorrifyingHealer card) {
        super(card);
    }

    @Override
    public AntiVenomHorrifyingHealer copy() {
        return new AntiVenomHorrifyingHealer(this);
    }
}

class AntiVenomHorrifyingHealerEffect extends PreventionEffectImpl {

    AntiVenomHorrifyingHealerEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false, false);
        staticText = "if damage would be dealt to {this}, prevent that damage and put that many +1/+1 counters on him";
    }

    private AntiVenomHorrifyingHealerEffect(final AntiVenomHorrifyingHealerEffect effect) {
        super(effect);
    }

    @Override
    public AntiVenomHorrifyingHealerEffect copy() {
        return new AntiVenomHorrifyingHealerEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game)
                && event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(event.getAmount()), source.getControllerId(), source, game);
        }
        return super.replaceEvent(event, source, game);
    }
}
