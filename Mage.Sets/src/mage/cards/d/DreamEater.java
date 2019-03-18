package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.SendOptionUsedEventEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class DreamEater extends CardImpl {

    public DreamEater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Dream Eater enters the battlefield, surveil 4. When you do, you may return target nonland permanent an opponent controls to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new SurveilEffect(4)
        );
        ability.addEffect(new DreamEaterCreateReflexiveTriggerEffect());
        this.addAbility(ability);
    }

    public DreamEater(final DreamEater card) {
        super(card);
    }

    @Override
    public DreamEater copy() {
        return new DreamEater(this);
    }
}

class DreamEaterCreateReflexiveTriggerEffect extends OneShotEffect {

    public DreamEaterCreateReflexiveTriggerEffect() {
        super(Outcome.Benefit);
        this.staticText = "When you do, you may return target "
                + "nonland permanent an opponent controls to its owner's hand.";
    }

    public DreamEaterCreateReflexiveTriggerEffect(final DreamEaterCreateReflexiveTriggerEffect effect) {
        super(effect);
    }

    @Override
    public DreamEaterCreateReflexiveTriggerEffect copy() {
        return new DreamEaterCreateReflexiveTriggerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addDelayedTriggeredAbility(new DreamEaterReflexiveTriggeredAbility(), source);
        return new SendOptionUsedEventEffect().apply(game, source);
    }
}

class DreamEaterReflexiveTriggeredAbility extends DelayedTriggeredAbility {

    private static final FilterPermanent filter
            = new FilterNonlandPermanent("nonland permanent an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public DreamEaterReflexiveTriggeredAbility() {
        super(new ReturnToHandTargetEffect());
        this.addTarget(new TargetPermanent(filter));
        this.optional = true;
    }

    public DreamEaterReflexiveTriggeredAbility(final DreamEaterReflexiveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DreamEaterReflexiveTriggeredAbility copy() {
        return new DreamEaterReflexiveTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.OPTION_USED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "When you surveil 4, you may return target nonland permanent "
                + "an opponent controls to its owner's hand.";
    }
}
