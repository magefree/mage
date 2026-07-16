package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;

/**
 *
 * @author muz
 */
public final class JocastaAutomatonAvenger extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("your commander");

    static {
        filter.add(CommanderPredicate.instance);
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public JocastaAutomatonAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever your commander deals combat damage to a player, put a +1/+1 counter on Jocasta.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
            filter, false, SetTargetPointer.NONE, true
        ));

        // Whenever you attack with your commander, if this card is in your graveyard, you may return it to the battlefield tapped and attacking.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
            Zone.GRAVEYARD,
            new JocastaAutomatonAvengerEffect(),
            1, filter, false, true
        ).setTriggerPhrase("Whenever you attack with your commander, if this card is in your graveyard, "));
    }

    private JocastaAutomatonAvenger(final JocastaAutomatonAvenger card) {
        super(card);
    }

    @Override
    public JocastaAutomatonAvenger copy() {
        return new JocastaAutomatonAvenger(this);
    }
}

class JocastaAutomatonAvengerEffect extends OneShotEffect {

    public JocastaAutomatonAvengerEffect() {
        super(Outcome.Benefit);
        staticText = "return it to the battlefield tapped and attacking";
    }

    private JocastaAutomatonAvengerEffect(final JocastaAutomatonAvengerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (controller != null && sourceCard != null && game.getState().getZone(sourceCard.getId()) == Zone.GRAVEYARD) {
            if (controller.moveCards(sourceCard, Zone.BATTLEFIELD, source, game, true, false, false, null)) {
                game.getCombat().addAttackingCreature(sourceCard.getId(), game);
            }
            return true;
        }
        return false;
    }

    @Override
    public JocastaAutomatonAvengerEffect copy() {
        return new JocastaAutomatonAvengerEffect(this);
    }
}
