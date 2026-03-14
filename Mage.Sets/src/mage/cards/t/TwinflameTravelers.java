package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class TwinflameTravelers extends CardImpl {

    public TwinflameTravelers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If a triggered ability of another Elemental you control triggers, it triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new TwinflameTravelersReplacementEffect()));
    }

    private TwinflameTravelers(final TwinflameTravelers card) {
        super(card);
    }

    @Override
    public TwinflameTravelers copy() {
        return new TwinflameTravelers(this);
    }
}

class TwinflameTravelersReplacementEffect extends ReplacementEffectImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    TwinflameTravelersReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a triggered ability of another Elemental you control triggers, "
                + "it triggers an additional time";
    }

    private TwinflameTravelersReplacementEffect(final TwinflameTravelersReplacementEffect effect) {
        super(effect);
    }

    @Override
    public TwinflameTravelersReplacementEffect copy() {
        return new TwinflameTravelersReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!source.isControlledBy(event.getPlayerId())) {
            return false;
        }
        Permanent permanentSource = game.getPermanentOrLKIBattlefield(event.getSourceId());
        return permanentSource != null
                && filter.match(permanentSource, source.getControllerId(), source, game)
                && permanentSource.hasSubtype(SubType.ELEMENTAL, game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 1));
        return false;
    }
}
