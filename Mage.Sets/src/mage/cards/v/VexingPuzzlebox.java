package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.OneOrMoreDiceRolledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RollDiceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VexingPuzzlebox extends CardImpl {

    public VexingPuzzlebox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever you roll one or more dice, put a number of charge counters on Vexing Puzzlebox equal to the result.
        this.addAbility(new OneOrMoreDiceRolledTriggeredAbility(new AddCountersSourceEffect(
                CounterType.CHARGE.createInstance(), VexingPuzzleboxValue.instance, true
        ).setText("put a number of charge counters on {this} equal to the result")));

        // {T}: Add one mana of any color. Roll a d20.
        AnyColorManaAbility manaAbility = new AnyColorManaAbility();
        manaAbility.addEffect(new RollDiceEffect(null, 20).setText("Roll a d20"));
        manaAbility.setUndoPossible(false);
        this.addAbility(manaAbility);

        // {T}, Remove 100 charge counters from Vexing Puzzlebox: Search your library for an artifact card, put that card onto the battlefield, then shuffle.
        Ability ability = new SimpleActivatedAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_ARTIFACT_AN)
        ), new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(100)));
        this.addAbility(ability);
    }

    private VexingPuzzlebox(final VexingPuzzlebox card) {
        super(card);
    }

    @Override
    public VexingPuzzlebox copy() {
        return new VexingPuzzlebox(this);
    }
}

enum VexingPuzzleboxValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return (Integer) effect.getValue("totalDieRoll");
    }

    @Override
    public VexingPuzzleboxValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
