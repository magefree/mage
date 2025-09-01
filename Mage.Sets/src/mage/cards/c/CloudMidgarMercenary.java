package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.permanent.EquippedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CloudMidgarMercenary extends CardImpl {

    private static final FilterCard filter = new FilterCard("an Equipment card");

    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
    }

    public CloudMidgarMercenary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Cloud enters, search your library for an Equipment card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true)
        ));

        // As long as Cloud is equipped, if an ability of Cloud or an Equipment attached to it triggers, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new CloudMidgarMercenaryEffect()));
    }

    private CloudMidgarMercenary(final CloudMidgarMercenary card) {
        super(card);
    }

    @Override
    public CloudMidgarMercenary copy() {
        return new CloudMidgarMercenary(this);
    }
}

class CloudMidgarMercenaryEffect extends ReplacementEffectImpl {

    CloudMidgarMercenaryEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "as long as {this} is equipped, if an ability of {this} " +
                "or an Equipment attached to it triggers, that ability triggers an additional time";
    }

    private CloudMidgarMercenaryEffect(final CloudMidgarMercenaryEffect effect) {
        super(effect);
    }

    @Override
    public CloudMidgarMercenaryEffect copy() {
        return new CloudMidgarMercenaryEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (sourcePermanent == null || !EquippedPredicate.instance.apply(sourcePermanent, game)) {
            return false;
        }
        NumberOfTriggersEvent numberOfTriggersEvent = (NumberOfTriggersEvent) event;
        GameEvent sourceEvent = numberOfTriggersEvent.getSourceEvent();
        if (sourceEvent == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(((NumberOfTriggersEvent) event).getSourceId());
        return permanent != null
                && (permanent.equals(sourcePermanent)
                || (permanent.hasSubtype(SubType.EQUIPMENT, game)
                && sourcePermanent.getAttachments().contains(permanent.getId())));
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 1));
        return false;
    }
}
