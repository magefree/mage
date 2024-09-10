package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.replacement.GainPlusOneLifeReplacementEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BilboBirthdayCelebrant extends CardImpl {

    public BilboBirthdayCelebrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // If you would gain life, you gain that much life plus 1 instead.
        this.addAbility(new SimpleStaticAbility(new GainPlusOneLifeReplacementEffect()));

        // {2}{W}{B}{G}, {T}, Exile Bilbo, Birthday Celebrant: Search your library for any number of creature cards, put them onto the battlefield, then shuffle. Activate only if you have 111 or more life.
        Ability ability = new ConditionalActivatedAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_CREATURES)
        ), new ManaCostsImpl<>("{2}{W}{B}{G}"), BilboBirthdayCelebrantCondition.instance);
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private BilboBirthdayCelebrant(final BilboBirthdayCelebrant card) {
        super(card);
    }

    @Override
    public BilboBirthdayCelebrant copy() {
        return new BilboBirthdayCelebrant(this);
    }
}

enum BilboBirthdayCelebrantCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getPlayer(source.getControllerId()).getLife() >= 111;
    }

    @Override
    public String toString() {
        return "you have 111 or more life";
    }
}