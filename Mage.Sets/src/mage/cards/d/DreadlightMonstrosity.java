package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;

import java.util.Collection;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DreadlightMonstrosity extends CardImpl {

    public DreadlightMonstrosity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.CRAB);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // {3}{U}{U}: Dreadlight Monstrosity can't be blocked this turn. Activate only if you own a card in exile.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                new CantBeBlockedSourceEffect(Duration.EndOfTurn),
                new ManaCostsImpl<>("{3}{U}{U}"), DreadlightMonstrosityCondition.instance
        ).addHint(DreadlightMonstrosityCondition.getHint()));
    }

    private DreadlightMonstrosity(final DreadlightMonstrosity card) {
        super(card);
    }

    @Override
    public DreadlightMonstrosity copy() {
        return new DreadlightMonstrosity(this);
    }
}

enum DreadlightMonstrosityCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getExile()
                .getExileZones()
                .stream()
                .flatMap(Collection::stream)
                .map(game::getOwnerId)
                .anyMatch(source::isControlledBy);
    }

    @Override
    public String toString() {
        return "you own a card in exile";
    }
}
