package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RosecotKnight extends CardImpl {

    public RosecotKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Rosecot Knight enters the battlefield, look at the top six cards of your library. You may reveal an artifact or enchantment card from among them and put it into your hand. Put the rest on the bottom of your library in a random order. If you didn't put a card into your hand this way, put a +1/+1 counter on Rosecot Knight.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RosecotKnightEffect()));
    }

    private RosecotKnight(final RosecotKnight card) {
        super(card);
    }

    @Override
    public RosecotKnight copy() {
        return new RosecotKnight(this);
    }
}

class RosecotKnightEffect extends LookLibraryAndPickControllerEffect {

    private static final FilterCard filter = new FilterCard("artifact or enchantment card");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.ENCHANTMENT.getPredicate()));
    }

    RosecotKnightEffect() {
        super(6, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM);
    }

    private RosecotKnightEffect(final RosecotKnightEffect effect) {
        super(effect);
    }

    @Override
    public RosecotKnightEffect copy() {
        return new RosecotKnightEffect(this);
    }

    @Override
    protected boolean actionWithPickedCards(Game game, Ability source, Player player, Cards pickedCards, Cards otherCards) {
        super.actionWithPickedCards(game, source, player, pickedCards, otherCards);
        pickedCards.retainZone(Zone.HAND, game);
        if (pickedCards.isEmpty()) {
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()).apply(game, source);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return super.getText(mode).concat(". If you didn't put a card into your hand this way, put a +1/+1 counter on {this}");
    }
}
