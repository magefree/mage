
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class Excavation extends CardImpl {

    public Excavation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // {1}, Sacrifice a land: Draw a card. Any player may activate this ability.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{1}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledLandPermanent("a land"))));
        ability.setMayActivate(TargetController.ANY);
        this.addAbility(ability);
    }

    private Excavation(final Excavation card) {
        super(card);
    }

    @Override
    public Excavation copy() {
        return new Excavation(this);
    }
}

class ExcavationEffect extends OneShotEffect {

    public ExcavationEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Draw a card. Any player may activate this ability";
    }

    public ExcavationEffect(final ExcavationEffect effect) {
        super(effect);
    }

    @Override
    public ExcavationEffect copy() {
        return new ExcavationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source instanceof ActivatedAbilityImpl) {
            Player activator = game.getPlayer(((ActivatedAbilityImpl) source).getActivatorId());
            if (activator != null) {
                activator.drawCards(1, source, game);
                return true;
            }

        }
        return false;
    }
}
