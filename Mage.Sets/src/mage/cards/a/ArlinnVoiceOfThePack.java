package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WolfToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArlinnVoiceOfThePack extends CardImpl {

    public ArlinnVoiceOfThePack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{G}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ARLINN);
        this.setStartingLoyalty(7);

        // Each creature you control that's a Wolf or Werewolf enters the battlefield with an additional +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(new ArlinnVoiceOfThePackReplacementEffect()));

        // -2: Create a 2/2 green Wolf creature token.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new WolfToken()), -2));
    }

    private ArlinnVoiceOfThePack(final ArlinnVoiceOfThePack card) {
        super(card);
    }

    @Override
    public ArlinnVoiceOfThePack copy() {
        return new ArlinnVoiceOfThePack(this);
    }
}

class ArlinnVoiceOfThePackReplacementEffect extends ReplacementEffectImpl {

    ArlinnVoiceOfThePackReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "Each creature you control that's a Wolf or a Werewolf " +
                "enters the battlefield with an additional +1/+1 counter on it";
    }

    private ArlinnVoiceOfThePackReplacementEffect(final ArlinnVoiceOfThePackReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        return creature != null && creature.isControlledBy(source.getControllerId())
                && creature.isCreature(game)
                && (creature.hasSubtype(SubType.WOLF, game)
                || creature.hasSubtype(SubType.WEREWOLF, game));
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null) {
            creature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
        }
        return false;
    }

    @Override
    public ArlinnVoiceOfThePackReplacementEffect copy() {
        return new ArlinnVoiceOfThePackReplacementEffect(this);
    }
}
