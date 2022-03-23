
package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInOpponentsGraveyard;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class AgadeemOccultist extends CardImpl {

    public AgadeemOccultist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.subtype.add(SubType.ALLY);

        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // {tap}: Put target creature card from an opponent's graveyard onto the battlefield under your control if its converted mana cost is less than or equal to the number of Allies you control.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AgadeemOccultistEffect(), new TapSourceCost()));

    }

    private AgadeemOccultist(final AgadeemOccultist card) {
        super(card);
    }

    @Override
    public AgadeemOccultist copy() {
        return new AgadeemOccultist(this);
    }
}

class AgadeemOccultistEffect extends OneShotEffect {

    public AgadeemOccultistEffect() {
        super(Outcome.GainControl);
        this.staticText = "Put target creature card from an opponent's graveyard onto the battlefield under your control if its mana value is less than or equal to the number of Allies you control";
    }

    public AgadeemOccultistEffect(final AgadeemOccultistEffect effect) {
        super(effect);
    }

    @Override
    public AgadeemOccultistEffect copy() {
        return new AgadeemOccultistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        int allycount = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            if (permanent.hasSubtype(SubType.ALLY, game)) {
                allycount++;
            }
        }
        FilterCard filter = new FilterCard("creature card in an opponent's graveyard");
        filter.add(CardType.CREATURE.getPredicate());
        TargetCardInOpponentsGraveyard target = new TargetCardInOpponentsGraveyard(1, 1, filter);

        if (controller != null) {
            if (target.canChoose(source.getControllerId(), source, game)
                    && controller.choose(Outcome.GainControl, target, source, game)) {
                if (!target.getTargets().isEmpty()) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        if (card.getManaValue() <= allycount) {
                            return controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
