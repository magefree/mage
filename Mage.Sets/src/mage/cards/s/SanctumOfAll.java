package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutOntoBattlefieldEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class SanctumOfAll extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();
    private static final FilterCard filterCard = new FilterCard("Shrine card");

    static final PermanentsOnBattlefieldCount count = new PermanentsOnBattlefieldCount(filter);

    static {
        filter.add(SubType.SHRINE.getPredicate());
        filterCard.add(SubType.SHRINE.getPredicate());
    }

    public SanctumOfAll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{U}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // At the beginning of your upkeep, you may search your library and/or graveyard for a Shrine card and put it onto the battlefield. If you search your library this way, shuffle it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SearchLibraryGraveyardPutOntoBattlefieldEffect(filterCard), TargetController.YOU, true));

        // If an ability of another Shrine you control triggers while you control six or more Shrines, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new SanctumOfAllTriggerEffect()).addHint(new ValueHint("Shrines you control", count)));
    }

    private SanctumOfAll(final SanctumOfAll card) {
        super(card);
    }

    @Override
    public SanctumOfAll copy() {
        return new SanctumOfAll(this);
    }
}

class SanctumOfAllTriggerEffect extends ReplacementEffectImpl {

    SanctumOfAllTriggerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if an ability of another Shrine you control triggers while you control six or more Shrines, that ability triggers an additional time";
    }

    private SanctumOfAllTriggerEffect(SanctumOfAllTriggerEffect effect) {
        super(effect);
    }

    @Override
    public SanctumOfAllTriggerEffect copy() {
        return new SanctumOfAllTriggerEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // Only triggers of the controller of Sanctum of All
        if (source.isControlledBy(event.getPlayerId())) {
            // Only trigger while you control six or more Shrines
            int numShrines = SanctumOfAll.count.calculate(game, source, this);
            if (numShrines >= 6) {
                // Only for triggers of other Shrines
                Permanent permanent = game.getPermanent(event.getSourceId());
                return permanent != null
                        && !permanent.getId().equals(source.getSourceId())
                        && permanent.hasSubtype(SubType.SHRINE, game);
            }
        }
        return false;
    }
}
