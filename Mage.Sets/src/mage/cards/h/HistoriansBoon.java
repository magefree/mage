package mage.cards.h;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.AngelVigilanceToken;
import mage.game.permanent.token.SoldierToken;
import mage.game.stack.StackObject;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HistoriansBoon extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledEnchantmentPermanent("another nontoken enchantment");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public HistoriansBoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // Whenever Historian's Boon or another nontoken enchantment enters the battlefield under your control, create a 1/1 white Soldier creature token.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new CreateTokenEffect(new SoldierToken()), filter, false, true
        ));

        // Whenever the final chapter of a Saga you control triggers, create a 4/4 white Angel creature token with flying and vigilance.
        this.addAbility(new HistoriansBoonTriggeredAbility());
    }

    private HistoriansBoon(final HistoriansBoon card) {
        super(card);
    }

    @Override
    public HistoriansBoon copy() {
        return new HistoriansBoon(this);
    }
}

class HistoriansBoonTriggeredAbility extends TriggeredAbilityImpl {

    HistoriansBoonTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new AngelVigilanceToken()));
    }

    private HistoriansBoonTriggeredAbility(final HistoriansBoonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HistoriansBoonTriggeredAbility copy() {
        return new HistoriansBoonTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TRIGGERED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        StackObject stackObject = game.getStack().getStackObject(event.getTargetId());
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (stackObject == null
                || permanent == null
                || !permanent.isControlledBy(getControllerId())
                || !permanent.hasSubtype(SubType.SAGA, game)) {
            return false;
        }
        int maxChapter = CardUtil
                .castStream(permanent.getAbilities(game).stream(), SagaAbility.class)
                .map(SagaAbility::getMaxChapter)
                .mapToInt(SagaChapter::getNumber)
                .sum();
        return SagaAbility.isFinalAbility(stackObject.getStackAbility(), maxChapter);
    }

    @Override
    public String getRule() {
        return "Whenever the final chapter of a Saga you control triggers, " +
                "create a 4/4 white Angel creature token with flying and vigilance.";
    }
}
