package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PerceptionBobblehead extends CardImpl {

    public PerceptionBobblehead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.BOBBLEHEAD);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {3}, {T}: Look at the top X cards of your library, where X is the number of Bobbleheads you control. You may cast a spell with mana value 3 or less from among them without paying its mana cost. Put the rest on the bottom of your library in a random order.
        Ability ability = new SimpleActivatedAbility(new PerceptionBobbleheadEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability.addHint(PerceptionBobbleheadEffect.getHint()));
    }

    private PerceptionBobblehead(final PerceptionBobblehead card) {
        super(card);
    }

    @Override
    public PerceptionBobblehead copy() {
        return new PerceptionBobblehead(this);
    }
}

class PerceptionBobbleheadEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.BOBBLEHEAD);
    private static final FilterCard filter2 = new FilterCard();

    static {
        filter2.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    private static final Hint hint = new ValueHint(
            "Bobbleheads you control", new PermanentsOnBattlefieldCount(filter)
    );

    public static Hint getHint() {
        return hint;
    }

    PerceptionBobbleheadEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top X cards of your library, where X is the number of Bobbleheads you control. " +
                "You may cast a spell with mana value 3 or less from among them without paying its mana cost. " +
                "Put the rest on the bottom of your library in a random order";
    }

    private PerceptionBobbleheadEffect(final PerceptionBobbleheadEffect effect) {
        super(effect);
    }

    @Override
    public PerceptionBobbleheadEffect copy() {
        return new PerceptionBobbleheadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int count = game.getBattlefield().count(filter, source.getControllerId(), source, game);
        if (player == null || count < 1) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, count));
        CardUtil.castSpellWithAttributesForFree(player, source, game, cards, filter2);
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
