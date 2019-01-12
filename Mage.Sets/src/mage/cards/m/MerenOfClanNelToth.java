
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class MerenOfClanNelToth extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public MerenOfClanNelToth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever another creature you control dies, you get an experience counter.
        Effect effect = new AddCountersControllerEffect(CounterType.EXPERIENCE.createInstance(1), false);
        effect.setText("you get an experience counter");
        this.addAbility(new DiesCreatureTriggeredAbility(effect, false, filter));
                
        // At the beginning of your end step, choose target creature card in your graveyard. 
        // If that card's converted mana cost is less than or equal to the number of experience counters you have, return it to the battlefield. Otherwise, put it into your hand.
        Target target = new TargetCardInYourGraveyard(new FilterCreatureCard("creature card in your graveyard"));
        Ability ability = new BeginningOfYourEndStepTriggeredAbility(new MerenOfClanNelTothEffect(), false);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    public MerenOfClanNelToth(final MerenOfClanNelToth card) {
        super(card);
    }

    @Override
    public MerenOfClanNelToth copy() {
        return new MerenOfClanNelToth(this);
    }
}

class MerenOfClanNelTothEffect extends OneShotEffect {

    public MerenOfClanNelTothEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "choose target creature card in your graveyard. If that card's converted mana cost is less than or equal to the number of experience counters you have, return it to the battlefield. Otherwise, put it into your hand";
    }

    public MerenOfClanNelTothEffect(final MerenOfClanNelTothEffect effect) {
        super(effect);
    }

    @Override
    public MerenOfClanNelTothEffect copy() {
        return new MerenOfClanNelTothEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int amount = player.getCounters().getCount(CounterType.EXPERIENCE);
            Card card = game.getCard(targetPointer.getFirst(game, source));
            if (card != null) {
                Zone targetZone = Zone.HAND;
                String text = " put into hand of ";
                if (card.getConvertedManaCost() <= amount) {
                    targetZone = Zone.BATTLEFIELD;
                    text = " put onto battlefield for ";
                }
                card.moveToZone(targetZone, source.getSourceId(), game, false);
                game.informPlayers("Meren of Clan Nel Toth: " + card.getName() + text + player.getLogName());
                return true;
            }
        }
        return false;
    }
}
