
package mage.cards.h;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public final class HeroesPodium extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Each legendary creature you control");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public HeroesPodium(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");
        addSuperType(SuperType.LEGENDARY);

        // Each legendary creature you control gets +1/+1 for each other legendary creature you control.
        DynamicValue xValue = new HeroesPodiumLegendaryCount();
        Effect effect = new BoostControlledEffect(xValue, xValue, Duration.WhileOnBattlefield, filter, false);
        effect.setText("Each legendary creature you control gets +1/+1 for each other legendary creature you control");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        // {X}, {T}: Look at the top X cards of your library. You may reveal a legendary creature card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HeroesPodiumEffect(), new ManaCostsImpl("{X}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private HeroesPodium(final HeroesPodium card) {
        super(card);
    }

    @Override
    public HeroesPodium copy() {
        return new HeroesPodium(this);
    }
}

class HeroesPodiumLegendaryCount implements DynamicValue {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("other legendary creature you control");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int value = game.getBattlefield().count(filter, sourceAbility.getControllerId(), sourceAbility, game);
        if (value > 0) {
            value--;
        }
        return value;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return filter.getMessage();
    }

    @Override
    public HeroesPodiumLegendaryCount copy() {
        return new HeroesPodiumLegendaryCount();
    }
}

class HeroesPodiumEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a legendary creature card");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public HeroesPodiumEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top X cards of your library. You may reveal a legendary creature card from among them and put it into your hand. Put the rest on the bottom of your library in a random order";
    }

    public HeroesPodiumEffect(final HeroesPodiumEffect effect) {
        super(effect);
    }

    @Override
    public HeroesPodiumEffect copy() {
        return new HeroesPodiumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }

        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, source.getManaCostsToPay().getX()));
        boolean legendaryIncluded = cards.count(filter, game) > 0;
        controller.lookAtCards(sourceObject.getIdName(), cards, game);

        // You may reveal a legendary creature card from among them and put it into your hand.
        if (!cards.isEmpty() && legendaryIncluded && controller.chooseUse(outcome, "Put a legendary creature card into your hand?", source, game)) {
            if (cards.size() == 1) {
                controller.moveCards(cards, Zone.HAND, source, game);
                return true;
            } else {
                TargetCard target = new TargetCard(Zone.LIBRARY, filter);
                if (controller.choose(outcome, cards, target, game)) {
                    Card card = cards.get(target.getFirstTarget(), game);
                    if (card != null) {
                        cards.remove(card);
                        controller.moveCards(card, Zone.HAND, source, game);
                    }
                }
            }
        }

        // Put the rest on the bottom of your library in a random order
        controller.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
