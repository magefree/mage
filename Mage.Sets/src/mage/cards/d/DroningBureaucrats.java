
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantAttackBlockAllEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;

/**
 *
 * @author TheElk801
 */
public final class DroningBureaucrats extends CardImpl {

    public DroningBureaucrats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // {X}, {tap}: Each creature with converted mana cost X can't attack or block this turn.
        Ability ability = new SimpleActivatedAbility(new DroningBureaucratsEffect(), new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private DroningBureaucrats(final DroningBureaucrats card) {
        super(card);
    }

    @Override
    public DroningBureaucrats copy() {
        return new DroningBureaucrats(this);
    }
}

class DroningBureaucratsEffect extends OneShotEffect {

    DroningBureaucratsEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each creature with mana value X can't attack or block this turn";
    }

    DroningBureaucratsEffect(final DroningBureaucratsEffect effect) {
        super(effect);
    }

    @Override
    public DroningBureaucratsEffect copy() {
        return new DroningBureaucratsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = source.getManaCostsToPay().getX();
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with mana value X");
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
        game.addEffect(new CantAttackBlockAllEffect(Duration.EndOfTurn, filter), source);
        return true;
    }
}
