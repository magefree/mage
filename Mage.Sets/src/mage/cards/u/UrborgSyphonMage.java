
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public final class UrborgSyphonMage extends CardImpl {

    public UrborgSyphonMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPELLSHAPER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}{B}, {tap}, Discard a card: Each other player loses 2 life. You gain life equal to the life lost this way.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UrborgSyphonMageEffect(), new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private UrborgSyphonMage(final UrborgSyphonMage card) {
        super(card);
    }

    @Override
    public UrborgSyphonMage copy() {
        return new UrborgSyphonMage(this);
    }
}

class UrborgSyphonMageEffect extends OneShotEffect {

    public UrborgSyphonMageEffect() {
        super(Outcome.Damage);
        staticText = "Each other player loses 2 life. You gain life equal to the life lost this way";
    }

    private UrborgSyphonMageEffect(final UrborgSyphonMageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = 0;
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (!source.isControlledBy(playerId)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        damage += player.loseLife(2, game, source, false);
                    }
                }
            }
            game.getPlayer(source.getControllerId()).gainLife(damage, game, source);
            return true;
        }
        return false;

    }

    @Override
    public UrborgSyphonMageEffect copy() {
        return new UrborgSyphonMageEffect(this);
    }

}
