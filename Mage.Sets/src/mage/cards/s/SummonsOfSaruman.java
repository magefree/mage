package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileXFromYourGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SummonsOfSaruman extends CardImpl {

    public SummonsOfSaruman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{R}");

        // Amass Orcs X. Mill X cards. You may cast an instant or sorcery spell with mana value X or less from among them without paying its mana cost. (To amass Orcs X, put X +1/+1 counters on an Army you control. It’s also an Orc. If you don’t control an Army, create a 0/0 black Orc Army creature token first.)
        this.getSpellAbility().addEffect(new AmassEffect(SummonsOfSarumanVariableValue.instance, SubType.ORC, false));
        this.getSpellAbility().addEffect(new SummonsOfSarumanEffect());

        // Flashback--{3}{U}{R}, Exile X cards from your graveyard.
        Ability flashback = new FlashbackAbility(this, new ManaCostsImpl<>("{3}{U}{R}"));
        flashback.addCost(new ExileXFromYourGraveCost(StaticFilters.FILTER_CARD_CARDS));
        this.addAbility(flashback);
    }

    private SummonsOfSaruman(final SummonsOfSaruman card) {
        super(card);
    }

    @Override
    public SummonsOfSaruman copy() {
        return new SummonsOfSaruman(this);
    }
}

enum SummonsOfSarumanVariableValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int xValue = sourceAbility.getManaCostsToPay().getX();
        for (Cost cost : sourceAbility.getCosts()) {
            if (cost instanceof ExileXFromYourGraveCost) {
                xValue = ((ExileXFromYourGraveCost) cost).getAmount();
            }
        }
        return xValue;
    }

    @Override
    public SummonsOfSarumanVariableValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "";
    }
}

class SummonsOfSarumanEffect extends OneShotEffect {

    SummonsOfSarumanEffect() {
        super(Outcome.Benefit);
        staticText = "Mill X cards. You may cast an instant or sorcery spell with mana value X "
                + "or less from among them without paying its mana cost."
                + " <i>(To amass Orcs X, put X +1/+1 counters on an Army you control. It's also an Orc. "
                + "If you don't control an Army, create a 0/0 black Orc Army creature token first.)</i>";
    }

    private SummonsOfSarumanEffect(final SummonsOfSarumanEffect effect) {
        super(effect);
    }

    @Override
    public SummonsOfSarumanEffect copy() {
        return new SummonsOfSarumanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int xValue = SummonsOfSarumanVariableValue.instance.calculate(game, source, this);
        if (player == null || xValue < 1) {
            return false;
        }
        Cards cards = player.millCards(xValue, source, game);
        cards.retainZone(Zone.GRAVEYARD, game);
        FilterCard filter = new FilterInstantOrSorceryCard();
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue + 1));
        CardUtil.castSpellWithAttributesForFree(player, source, game, cards, filter);

        return true;
    }
}