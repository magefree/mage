package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author LevelX
 */
public final class SenseisDiviningTop extends CardImpl {

    public SenseisDiviningTop(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {1}: Look at the top three cards of your library, then put them back in any order.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new LookLibraryControllerEffect(3), new GenericManaCost(1)));
        // {T}: Draw a card, then put Sensei's Divining Top on top of its owner's library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new TapSourceCost());
        ability.addEffect(new SenseisDiviningTopEffect());
        this.addAbility(ability);
    }

    private SenseisDiviningTop(final SenseisDiviningTop card) {
        super(card);
    }

    @Override
    public SenseisDiviningTop copy() {
        return new SenseisDiviningTop(this);
    }

}

class SenseisDiviningTopEffect extends OneShotEffect {

    public SenseisDiviningTopEffect() {
        super(Outcome.ReturnToHand);
        staticText = ", then put {this} on top of its owner's library";
    }

    public SenseisDiviningTopEffect(final SenseisDiviningTopEffect effect) {
        super(effect);
    }

    @Override
    public SenseisDiviningTopEffect copy() {
        return new SenseisDiviningTopEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player owner = game.getPlayer(game.getOwnerId(source.getSourceId()));
        if (permanent != null && owner != null) {
            return owner.putCardsOnTopOfLibrary(permanent, game, source, true);
        }
        return false;
    }
}
