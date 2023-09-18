
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
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
 * @author LevelX2
 */
public final class MistmoonGriffin extends CardImpl {

    public MistmoonGriffin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.GRIFFIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Mistmoon Griffin dies, exile Mistmoon Griffin, then return the top creature card of your graveyard to the battlefield.
        Ability ability = new DiesSourceTriggeredAbility(new ExileSourceEffect());
        ability.addEffect(new MistmoonGriffinEffect());
        this.addAbility(ability);

    }

    private MistmoonGriffin(final MistmoonGriffin card) {
        super(card);
    }

    @Override
    public MistmoonGriffin copy() {
        return new MistmoonGriffin(this);
    }
}

class MistmoonGriffinEffect extends OneShotEffect {

    public MistmoonGriffinEffect() {
        super(Outcome.Benefit);
        this.staticText = ", then return the top creature card of your graveyard to the battlefield";
    }

    private MistmoonGriffinEffect(final MistmoonGriffinEffect effect) {
        super(effect);
    }

    @Override
    public MistmoonGriffinEffect copy() {
        return new MistmoonGriffinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card lastCreatureCard = null;
            for (Card card : controller.getGraveyard().getCards(game)) {
                if (card.isCreature(game)) {
                    lastCreatureCard = card;
                }
            }
            if (lastCreatureCard != null) {
                return controller.moveCards(lastCreatureCard, Zone.BATTLEFIELD, source, game);
            }
            return true;
        }
        return false;
    }
}
