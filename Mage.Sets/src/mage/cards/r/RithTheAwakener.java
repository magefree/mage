
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.token.SaprolingToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class RithTheAwakener extends CardImpl {

    public RithTheAwakener(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Rith, the Awakener deals combat damage to a player, you may pay {2}{G}. If you do, choose a color, then create a 1/1 green Saproling creature token for each permanent of that color.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DoIfCostPaid(new RithTheAwakenerEffect(), new ManaCostsImpl("{2}{G}")), false));
    }

    private RithTheAwakener(final RithTheAwakener card) {
        super(card);
    }

    @Override
    public RithTheAwakener copy() {
        return new RithTheAwakener(this);
    }
}

class RithTheAwakenerEffect extends OneShotEffect {

    public RithTheAwakenerEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose a color, then create a 1/1 green Saproling creature token for each permanent of that color";
    }

    public RithTheAwakenerEffect(final RithTheAwakenerEffect effect) {
        super(effect);
    }

    @Override
    public RithTheAwakenerEffect copy() {
        return new RithTheAwakenerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        ChoiceColor choice = new ChoiceColor();
        if (controller.choose(outcome, choice, game)) {
            game.informPlayers(controller.getLogName() + " chooses " + choice.getColor());
            FilterPermanent filter = new FilterPermanent();
            filter.add(new ColorPredicate(choice.getColor()));
            int cardsWithColor = game.getBattlefield().count(filter, controller.getId(), source, game);
            if (cardsWithColor > 0) {
                new CreateTokenEffect(new SaprolingToken(), cardsWithColor).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
