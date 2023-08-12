
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class DromarTheBanisher extends CardImpl {

    public DromarTheBanisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Dromar, the Banisher deals combat damage to a player, you may pay {2}{U}. If you do, choose a color, then return all creatures of that color to their owners' hands.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DoIfCostPaid(new DromarTheBanisherEffect(), new ManaCostsImpl<>("{2}{U}")), false));
    }

    private DromarTheBanisher(final DromarTheBanisher card) {
        super(card);
    }

    @Override
    public DromarTheBanisher copy() {
        return new DromarTheBanisher(this);
    }
}

class DromarTheBanisherEffect extends OneShotEffect {

    DromarTheBanisherEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "choose a color, then return all creatures of that color to their owners' hands.";
    }

    DromarTheBanisherEffect(final DromarTheBanisherEffect effect) {
        super(effect);
    }

    @Override
    public DromarTheBanisherEffect copy() {
        return new DromarTheBanisherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            ChoiceColor choice = new ChoiceColor();
            if (player.choose(outcome, choice, game)) {
                game.informPlayers(player.getLogName() + " chooses " + choice.getChoice());
                FilterCreaturePermanent filter = new FilterCreaturePermanent();
                filter.add(new ColorPredicate(choice.getColor()));
                new ReturnToHandFromBattlefieldAllEffect(filter).apply(game, source);
                return true;
            }
        }
        return false;
    }
}
