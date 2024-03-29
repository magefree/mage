package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.game.Game;
import mage.target.TargetSpell;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StellaLeeWildCard extends CardImpl {

    private static final FilterSpell filter = new FilterInstantOrSorcerySpell("instant or sorcery spell you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public StellaLeeWildCard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you cast your second spell each turn, exile the top card of your library. Until the end of your next turn, you may play that card.
        this.addAbility(new CastSecondSpellTriggeredAbility(
                new ExileTopXMayPlayUntilEffect(1, Duration.UntilEndOfYourNextTurn)
        ));

        // {T}: Copy target instant or sorcery spell you control. You may choose new targets for the copy. Activate only if you've cast three or more spells this turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new CopyTargetSpellEffect(),
                new TapSourceCost(), StellaLeeWildCardCondition.instance
        );
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability.addHint(StormAbility.getHint()));
    }

    private StellaLeeWildCard(final StellaLeeWildCard card) {
        super(card);
    }

    @Override
    public StellaLeeWildCard copy() {
        return new StellaLeeWildCard(this);
    }
}

enum StellaLeeWildCardCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(CastSpellLastTurnWatcher.class)
                .getAmountOfSpellsPlayerCastOnCurrentTurn(source.getControllerId()) >= 3;
    }

    @Override
    public String toString() {
        return "you've cast three or more spells this turn";
    }
}
