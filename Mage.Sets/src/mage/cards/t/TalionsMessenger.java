package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TalionsMessenger extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.FAERIE, "Faeries");

    public TalionsMessenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you attack with one or more Faeries, draw a card, then discard a card. When you discard a card this way, put a +1/+1 counter on target Faerie you control.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new TalionsMessengerEffect(), 1, filter
        ));
    }

    private TalionsMessenger(final TalionsMessenger card) {
        super(card);
    }

    @Override
    public TalionsMessenger copy() {
        return new TalionsMessenger(this);
    }
}

class TalionsMessengerEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent(SubType.FAERIE, "Faerie you control");

    TalionsMessengerEffect() {
        super(Outcome.Benefit);
        staticText = "draw a card, then discard a card. When you discard a card this way, put a +1/+1 counter on target Faerie you control";
    }

    private TalionsMessengerEffect(final TalionsMessengerEffect effect) {
        super(effect);
    }

    @Override
    public TalionsMessengerEffect copy() {
        return new TalionsMessengerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.drawCards(1, source, game);
        Cards discard = controller.discard(1, false, false, source, game);
        if (!discard.isEmpty()) {
            ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
                    new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                    false
            );
            reflexive.addTarget(new TargetPermanent(filter));
            game.fireReflexiveTriggeredAbility(reflexive, source);
        }
        return true;
    }

}