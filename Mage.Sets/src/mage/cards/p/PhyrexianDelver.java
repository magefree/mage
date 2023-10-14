
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class PhyrexianDelver extends CardImpl {

    public PhyrexianDelver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Phyrexian Delver enters the battlefield, return target creature card from your graveyard to the battlefield. You lose life equal to that card's converted mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PhyrexianDelverEffect(), false);
        Target target = new TargetCardInYourGraveyard(1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private PhyrexianDelver(final PhyrexianDelver card) {
        super(card);
    }

    @Override
    public PhyrexianDelver copy() {
        return new PhyrexianDelver(this);
    }
}

class PhyrexianDelverEffect extends OneShotEffect {

    public PhyrexianDelverEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "return target creature card from your graveyard to the battlefield. You lose life equal to that card's mana value";
    }

    private PhyrexianDelverEffect(final PhyrexianDelverEffect effect) {
        super(effect);
    }

    @Override
    public PhyrexianDelverEffect copy() {
        return new PhyrexianDelverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card creatureCard = game.getCard(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (creatureCard != null && controller != null) {
            boolean result = false;
            if (game.getState().getZone(creatureCard.getId()) == Zone.GRAVEYARD) {
                result = controller.moveCards(creatureCard, Zone.BATTLEFIELD, source, game);
            }
            controller.loseLife(creatureCard.getManaValue(), game, source, false);
            return result;
        }
        return false;
    }
}
