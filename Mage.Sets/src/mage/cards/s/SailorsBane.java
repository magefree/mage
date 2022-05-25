package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.WardAbility;
import mage.cards.AdventureCard;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SailorsBane extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Instant, sorcery, and Adventure cards in your graveyard and in exile", SailorsBaneValue.instance
    );

    public SailorsBane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{U}{U}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // This spell costs {1} less to cast for each card you own in exile and in your graveyard that's an instant card, a sorcery card, or a card that has an Adventure.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(SailorsBaneValue.instance)
                .setText("this spell costs {1} less to cast for each card you own in exile and in " +
                        "your graveyard that's an instant card, a sorcery card, or a card that has an Adventure")
        ).addHint(hint).setRuleAtTheTop(true));

        // Ward {4}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{4}")));
    }

    private SailorsBane(final SailorsBane card) {
        super(card);
    }

    @Override
    public SailorsBane copy() {
        return new SailorsBane(this);
    }
}

enum SailorsBaneValue implements DynamicValue {
    instance;
    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate(),
                SailorsBaneValue::checkAdventure
        ));
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .of(game.getPlayer(sourceAbility.getControllerId()))
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .map(graveyard -> graveyard.count(filter, game))
                .orElse(0)
                + game
                .getExile()
                .getCards(filter, game)
                .size();
    }

    @Override
    public SailorsBaneValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    private static final boolean checkAdventure(Card input, Game game) {
        return input instanceof AdventureCard;
    }
}
