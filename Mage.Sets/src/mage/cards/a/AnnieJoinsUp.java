package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AnnieJoinsUp extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public AnnieJoinsUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);

        // When Annie Joins Up enters the battlefield, it deals 5 damage to target creature or planeswalker an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(5));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // If a triggered ability of a legendary creature you control triggers, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new AnnieJoinsUpEffect()));
    }

    private AnnieJoinsUp(final AnnieJoinsUp card) {
        super(card);
    }

    @Override
    public AnnieJoinsUp copy() {
        return new AnnieJoinsUp(this);
    }
}

class AnnieJoinsUpEffect extends ReplacementEffectImpl {

    AnnieJoinsUpEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if a triggered ability of a legendary creature you control triggers, " +
                "that ability triggers an additional time";
    }

    private AnnieJoinsUpEffect(final AnnieJoinsUpEffect effect) {
        super(effect);
    }

    @Override
    public AnnieJoinsUpEffect copy() {
        return new AnnieJoinsUpEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!(event instanceof NumberOfTriggersEvent)) {
            return false;
        }
        Permanent permanent = game.getPermanent(((NumberOfTriggersEvent) event).getSourceId());
        return permanent != null
                && permanent.isControlledBy(source.getControllerId())
                && permanent.isLegendary(game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 1));
        return false;
    }
}
