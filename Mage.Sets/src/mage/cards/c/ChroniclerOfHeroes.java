
package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class ChroniclerOfHeroes extends CardImpl {

    public ChroniclerOfHeroes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{W}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Chronicler of Heroes enters the battlefield, draw a card if you control a creature with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ChroniclerOfHeroesEffect()));
    }

    private ChroniclerOfHeroes(final ChroniclerOfHeroes card) {
        super(card);
    }

    @Override
    public ChroniclerOfHeroes copy() {
        return new ChroniclerOfHeroes(this);
    }
}

class ChroniclerOfHeroesEffect extends OneShotEffect {

    public ChroniclerOfHeroesEffect() {
        super(Outcome.DrawCard);
        this.staticText = "draw a card if you control a creature with a +1/+1 counter on it";
    }

    public ChroniclerOfHeroesEffect(final ChroniclerOfHeroesEffect effect) {
        super(effect);
    }

    @Override
    public ChroniclerOfHeroesEffect copy() {
        return new ChroniclerOfHeroesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_A_CREATURE_P1P1).apply(game, source)) {
                controller.drawCards(1, source, game);
            }
            return true;
        }
        return false;
    }
}
