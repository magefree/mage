package mage.cards.a;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.OpponentsLostLifeCount;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.CascadeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.game.Game;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AbaddonTheDespoiler extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(new CastFromZonePredicate(Zone.HAND));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(AbaddonTheDespoilerPredicate.instance);
    }

    private static final Hint hint = new ValueHint(
            "Total life lost by opponents this turn", OpponentsLostLifeCount.instance
    );

    public AbaddonTheDespoiler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ASTARTES);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Mark of Chaos Ascendant — During your turn, spells you cast from your hand with mana value X or less have cascade, where X is the total amount of life your opponents have lost this turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityControlledSpellsEffect(new CascadeAbility(false), filter),
                MyTurnCondition.instance, "during your turn, spells you cast from " +
                "your hand with mana value X or less have cascade, where X is the " +
                "total amount of life your opponents have lost this turn"
        )).addHint(hint).withFlavorWord("Mark of Chaos Ascendant"));
    }

    private AbaddonTheDespoiler(final AbaddonTheDespoiler card) {
        super(card);
    }

    @Override
    public AbaddonTheDespoiler copy() {
        return new AbaddonTheDespoiler(this);
    }
}

enum AbaddonTheDespoilerPredicate implements Predicate<MageObject> {
    instance;

    @Override
    public boolean apply(MageObject input, Game game) {
        if (input instanceof Card) {
            Card card = (Card) input;
            return card.getManaValue() <= game
                    .getState()
                    .getWatcher(PlayerLostLifeWatcher.class)
                    .getAllOppLifeLost(card.getOwnerId(), game);
        }
        return false;
    }
}
