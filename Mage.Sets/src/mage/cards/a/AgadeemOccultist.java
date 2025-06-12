
package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
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
        Ability ability = new SimpleActivatedAbility(new AgadeemOccultistEffect(), new TapSourceCost());
        ability.addTarget(new TargetCardInOpponentsGraveyard(new FilterCreatureCard("target creature card from an opponent's graveyard")));
        ability.addHint(new ValueHint("Allies you control", new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.ALLY))));
        this.addAbility(ability);

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

    AgadeemOccultistEffect() {
        super(Outcome.GainControl);
        this.staticText = "Put target creature card from an opponent's graveyard onto the battlefield under your control if its mana value is less than or equal to the number of Allies you control";
    }

    private AgadeemOccultistEffect(final AgadeemOccultistEffect effect) {
        super(effect);
    }

    @Override
    public AgadeemOccultistEffect copy() {
        return new AgadeemOccultistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(source.getFirstTarget());
            if (card != null) {
                int allycount = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.ALLY)).calculate(game, source, this);
                if (card.getManaValue() <= allycount) {
                    return controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
