package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.cost.CastFromHandForFreeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.util.CardUtil;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class TwinningGlass extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(TwinningGlassPredicate.instance);
    }

    public TwinningGlass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {1}, {T}: You may cast a nonland card from your hand without paying its mana cost if it has the same name as a spell that was cast this turn.
        Ability ability = new SimpleActivatedAbility(
                new CastFromHandForFreeEffect(filter).setText(
                        "you may cast a spell from your hand without paying its mana cost " +
                                "if it has the same name as a spell that was cast this turn"
                ), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability, new SpellsCastWatcher());
    }

    private TwinningGlass(final TwinningGlass card) {
        super(card);
    }

    @Override
    public TwinningGlass copy() {
        return new TwinningGlass(this);
    }
}

enum TwinningGlassPredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        return watcher != null && watcher
                .getAllSpellsCastThisTurn()
                .anyMatch(spell -> CardUtil.haveSameNames(spell, input));
    }
}
