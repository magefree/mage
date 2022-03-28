
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
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
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class TrevaTheRenewer extends CardImpl {

    public TrevaTheRenewer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{W}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Treva, the Renewer deals combat damage to a player, you may pay {2}{W}. If you do, choose a color, then you gain 1 life for each permanent of that color.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DoIfCostPaid(new TrevaTheRenewerEffect(), new ManaCostsImpl("{2}{W}")), false));
    }

    private TrevaTheRenewer(final TrevaTheRenewer card) {
        super(card);
    }

    @Override
    public TrevaTheRenewer copy() {
        return new TrevaTheRenewer(this);
    }
}

class TrevaTheRenewerEffect extends OneShotEffect {

    public TrevaTheRenewerEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose a color, then you gain 1 life for each permanent of that color.";
    }

    public TrevaTheRenewerEffect(final TrevaTheRenewerEffect effect) {
        super(effect);
    }

    @Override
    public TrevaTheRenewerEffect copy() {
        return new TrevaTheRenewerEffect(this);
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
                new GainLifeEffect(cardsWithColor).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
