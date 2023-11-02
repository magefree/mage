package mage.cards.q;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class QuarryColossus extends CardImpl {

    public QuarryColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // When Quarry Colossus enters the battlefield, put target creature into its owner's 
        // library just beneath the top X cards of that library, where X is the number of Plains you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new QuarryColossusReturnLibraryEffect(), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private QuarryColossus(final QuarryColossus card) {
        super(card);
    }

    @Override
    public QuarryColossus copy() {
        return new QuarryColossus(this);
    }
}

class QuarryColossusReturnLibraryEffect extends OneShotEffect {

    public QuarryColossusReturnLibraryEffect() {
        super(Outcome.Detriment);
        this.staticText = "put target creature into its owner's library just beneath the "
                + "top X cards of that library, where X is the number of Plains you control";
    }

    private QuarryColossusReturnLibraryEffect(final QuarryColossusReturnLibraryEffect effect) {
        super(effect);
    }

    @Override
    public QuarryColossusReturnLibraryEffect copy() {
        return new QuarryColossusReturnLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (permanent != null && controller != null) {
            Player owner = game.getPlayer(permanent.getOwnerId());
            if (owner != null) {
                int plains = game.getBattlefield().countAll(new FilterPermanent(
                        SubType.PLAINS, "Plains you control"), source.getControllerId(), game);
                controller.putCardOnTopXOfLibrary(permanent, game, source, plains + 1, true);
                return true;
            }
        }
        return false;
    }
}
