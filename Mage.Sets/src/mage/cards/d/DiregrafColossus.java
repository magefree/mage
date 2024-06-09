
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class DiregrafColossus extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Zombie spell");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public DiregrafColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Diregraf Colossus enters the battlefield with a +1/+1 counter on it for each Zombie card in your graveyard.
        this.addAbility(new EntersBattlefieldAbility(new DiregrafColossusEffect(), "with a +1/+1 counter on it for each Zombie card in your graveyard"));

        // Whenever you cast a Zombie spell, create a tapped 2/2 black Zombie creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new ZombieToken(), 1, true, false), filter, false));

    }

    private DiregrafColossus(final DiregrafColossus card) {
        super(card);
    }

    @Override
    public DiregrafColossus copy() {
        return new DiregrafColossus(this);
    }
}

class DiregrafColossusEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public DiregrafColossusEffect() {
        super(Outcome.BoostCreature);
        staticText = "{this} enters the battlefield with a +1/+1 counter on it for each Zombie card in your graveyard";
    }

    private DiregrafColossusEffect(final DiregrafColossusEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent != null && player != null) {
            int amount = 0;
            amount += player.getGraveyard().count(filter, game);
            if (amount > 0) {
                permanent.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public DiregrafColossusEffect copy() {
        return new DiregrafColossusEffect(this);
    }

}
