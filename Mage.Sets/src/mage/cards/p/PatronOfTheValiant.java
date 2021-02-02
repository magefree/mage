
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class PatronOfTheValiant extends CardImpl {

    public PatronOfTheValiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Patron of the Valiant enters the battlefield, put a +1/+1 counter on each creature you control with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PatronOfTheValiantEffect()));
    }

    private PatronOfTheValiant(final PatronOfTheValiant card) {
        super(card);
    }

    @Override
    public PatronOfTheValiant copy() {
        return new PatronOfTheValiant(this);
    }
}

class PatronOfTheValiantEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(CounterType.P1P1.getPredicate());
    }

    public PatronOfTheValiantEffect() {
        super(Outcome.Benefit);
        this.staticText = "put a +1/+1 counter on each creature you control with a +1/+1 counter on it.";
    }

    public PatronOfTheValiantEffect(final PatronOfTheValiantEffect effect) {
        super(effect);
    }

    @Override
    public PatronOfTheValiantEffect copy() {
        return new PatronOfTheValiantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            for(Permanent permanent: game.getState().getBattlefield().getAllActivePermanents(filter , controller.getId(), game)) {
                permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
                game.informPlayers(sourceObject.getName() + ": Put a +1/+1 counter on " + permanent.getLogName());
            }
        }
        return true;
    }
}
