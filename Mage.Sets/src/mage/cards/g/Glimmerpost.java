

package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class Glimmerpost extends CardImpl {

    public Glimmerpost (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);
        this.subtype.add(SubType.LOCUS);
        
        // When Glimmerpost enters the battlefield, you gain 1 life for each Locus on the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GlimmerpostEffect()));
        
        // {T}: Add to {1} your mana pool.
        this.addAbility(new ColorlessManaAbility());
    }

    public Glimmerpost (final Glimmerpost card) {
        super(card);
    }

    @Override
    public Glimmerpost copy() {
        return new Glimmerpost(this);
    }

}

class GlimmerpostEffect extends OneShotEffect {
    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(SubType.LOCUS.getPredicate());
    }

    public GlimmerpostEffect() {
        super(Outcome.GainLife);
        staticText = "you gain 1 life for each Locus on the battlefield";
    }

    public GlimmerpostEffect(final GlimmerpostEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = game.getBattlefield().count(filter, source.getControllerId(), source, game);
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.gainLife(amount, game, source);
            return true;
        }
        return false;
    }

    @Override
    public GlimmerpostEffect copy() {
        return new GlimmerpostEffect(this);
    }

}