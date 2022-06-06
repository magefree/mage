package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class MetathranAerostat extends CardImpl {

    public MetathranAerostat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.METATHRAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {X}{U}: You may put a creature card with converted mana cost X from your hand onto the battlefield. If you do, return Metathran Aerostat to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(new MetathranAerostatEffect(), new ManaCostsImpl<>("{X}{U}")));
    }

    private MetathranAerostat(final MetathranAerostat card) {
        super(card);
    }

    @Override
    public MetathranAerostat copy() {
        return new MetathranAerostat(this);
    }
}

class MetathranAerostatEffect extends OneShotEffect {

    public MetathranAerostatEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may put a creature card with mana value "
                + "X from your hand onto the battlefield. "
                + "If you do, return {this} to its owner's hand";
    }

    public MetathranAerostatEffect(final MetathranAerostatEffect effect) {
        super(effect);
    }

    @Override
    public MetathranAerostatEffect copy() {
        return new MetathranAerostatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int xValue = source.getManaCostsToPay().getX();
        FilterCreatureCard filter = new FilterCreatureCard("a creature with mana value " + xValue);
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
        if (new PutCardFromHandOntoBattlefieldEffect(filter).apply(game, source)) {
            return new ReturnToHandSourceEffect(true).apply(game, source);
        }
        return false;
    }
}
