package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.ChancellorAbility;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DevourerOfDestiny extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent that's one or more colors");

    static {
        filter.add(Predicates.not(ColorlessPredicate.instance));
    }

    public DevourerOfDestiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{C}{C}");

        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // You may reveal this card from your opening hand. If you do, at the beginning of your first upkeep, look at the top four cards of your library. You may put one of those cards back on top of your library. Exile the rest.
        this.addAbility(new ChancellorAbility(
                new DevourerOfDestinyTriggeredAbility(), "at the beginning of your first upkeep, " +
                "look at the top four cards of your library. You may put one of those cards " +
                "back on top of your library. Exile the rest"
        ));

        // When you cast this spell, exile target permanent that's one or more colors.
        Ability ability = new CastSourceTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private DevourerOfDestiny(final DevourerOfDestiny card) {
        super(card);
    }

    @Override
    public DevourerOfDestiny copy() {
        return new DevourerOfDestiny(this);
    }
}

class DevourerOfDestinyTriggeredAbility extends DelayedTriggeredAbility {

    DevourerOfDestinyTriggeredAbility() {
        super(new LookLibraryAndPickControllerEffect(4, 1, PutCards.TOP_ANY, PutCards.EXILED, true));
    }

    private DevourerOfDestinyTriggeredAbility(final DevourerOfDestinyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public DevourerOfDestinyTriggeredAbility copy() {
        return new DevourerOfDestinyTriggeredAbility(this);
    }
}
