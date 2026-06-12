package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.watchers.common.DiscardedCardWatcher;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class MistyKnightHeroForHire extends CardImpl {

    public MistyKnightHeroForHire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {2}, {T}, Discard a card: Draw a card for each card you've discarded this turn.
        Ability ability = new SimpleActivatedAbility(
            new DrawCardSourceControllerEffect(MistyKnightHeroForHireValue.instance), new ManaCostsImpl<>("{2}")
                .setText("draw a card for each card you've discarded this turn")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability.addHint(MistyKnightHeroForHireValue.getHint()), new DiscardedCardWatcher());
    }

    private MistyKnightHeroForHire(final MistyKnightHeroForHire card) {
        super(card);
    }

    @Override
    public MistyKnightHeroForHire copy() {
        return new MistyKnightHeroForHire(this);
    }
}

enum MistyKnightHeroForHireValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("Cards you've discarded this turn", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return DiscardedCardWatcher.getDiscarded(sourceAbility.getControllerId(), game);
    }

    @Override
    public MistyKnightHeroForHireValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "card you've discarded this turn";
    }
}
