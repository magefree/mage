
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class NinThePainArtist extends CardImpl {

    public NinThePainArtist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {X}{U}{R}, {tap}: Nin, the Pain Artist deals X damage to target creature. That creature's controller draws X cards.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new NinThePainArtistEffect(), new ManaCostsImpl<>("{X}{U}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private NinThePainArtist(final NinThePainArtist card) {
        super(card);
    }

    @Override
    public NinThePainArtist copy() {
        return new NinThePainArtist(this);
    }
}

class NinThePainArtistEffect extends OneShotEffect {
    
    NinThePainArtistEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals X damage to target creature. That creature's controller draws X cards.";
    }
    
    NinThePainArtistEffect(final NinThePainArtistEffect effect) {
        super(effect);
    }
    
    @Override
    public NinThePainArtistEffect copy() {
        return new NinThePainArtistEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            permanent.damage(source.getManaCostsToPay().getX(), source.getSourceId(), source, game, false, true);
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                player.drawCards(source.getManaCostsToPay().getX(), source, game);
            }
            return true;
        }
        return false;
    }
}
