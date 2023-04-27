
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public final class ScavengingOoze extends CardImpl {

    public ScavengingOoze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.OOZE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {G}: Exile target card from a graveyard. If it was a creature card, put a +1/+1 counter on Scavenging Ooze and you gain 1 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScavengingOozeEffect(), new ManaCostsImpl<>("{G}"));
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);
    }

    private ScavengingOoze(final ScavengingOoze card) {
        super(card);
    }

    @Override
    public ScavengingOoze copy() {
        return new ScavengingOoze(this);
    }
}

class ScavengingOozeEffect extends OneShotEffect {

    public ScavengingOozeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile target card from a graveyard. If it was a creature card, put a +1/+1 counter on {this} and you gain 1 life";
    }

    public ScavengingOozeEffect(final ScavengingOozeEffect effect) {
        super(effect);
    }

    @Override
    public ScavengingOozeEffect copy() {
        return new ScavengingOozeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && card != null) {
            controller.moveCardToExileWithInfo(card, null, "", source, game, Zone.GRAVEYARD, true);
            if (card.isCreature(game)) {
                Permanent sourcePermanent = game.getPermanent(source.getSourceId());
                if (sourcePermanent != null) {
                    sourcePermanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
                }
                controller.gainLife(1, game, source);
            }
            return true;
        }
        return false;
    }
    
}
