package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WolfToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArlinnThePacksHope extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature spells");

    public ArlinnThePacksHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ARLINN);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(4));
        this.secondSideCardClazz = mage.cards.a.ArlinnTheMoonsFury.class;

        // Daybound
        this.addAbility(new TransformAbility());
        this.addAbility(new DayboundAbility());

        // +1: Until your next turn, you may cast creature spells as though they had flash, and each creature you control enters the battlefield with an additional +1/+1 counter on it.
        Ability ability = new LoyaltyAbility(new CastAsThoughItHadFlashAllEffect(
                Duration.UntilYourNextTurn, filter
        ).setText("until your next turn, you may cast creature spells as though they had flash"), 1);
        ability.addEffect(new ArlinnThePacksHopeEffect());
        this.addAbility(ability);

        // −3: Create two 2/2 green Wolf creature tokens.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new WolfToken(), 2), -3));
    }

    private ArlinnThePacksHope(final ArlinnThePacksHope card) {
        super(card);
    }

    @Override
    public ArlinnThePacksHope copy() {
        return new ArlinnThePacksHope(this);
    }
}

class ArlinnThePacksHopeEffect extends ReplacementEffectImpl {

    ArlinnThePacksHopeEffect() {
        super(Duration.UntilYourNextTurn, Outcome.BoostCreature);
        this.staticText = ", and each creature you control enters the battlefield with an additional +1/+1 counter on it";
    }

    private ArlinnThePacksHopeEffect(ArlinnThePacksHopeEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        return permanent != null && permanent.isControlledBy(source.getControllerId()) && permanent.isCreature(game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        if (target != null) {
            target.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
        }
        return false;
    }

    @Override
    public ArlinnThePacksHopeEffect copy() {
        return new ArlinnThePacksHopeEffect(this);
    }
}
