package mage.cards.r;

import mage.ConditionalMana;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.XCostManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.VariableManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class RosheenRoaringProphet extends CardImpl {

    private static final FilterCard filter = new FilterCard("a card with {X} in its mana cost");

    static {
        filter.add(VariableManaCostPredicate.instance);
    }

    public RosheenRoaringProphet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Rosheen, Roaring Prophet enters the battlefield, mill six cards. You may put a card with {X} in its mana cost from among them into your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new MillThenPutInHandEffect(6, filter), true
        ));

        // {T}: Reveal any number of cards with {X} in their mana cost in your hand. Add {C}{C} for each card revealed this way. Spend this mana only on costs that contain {X}.
        SimpleManaAbility ability = new SimpleManaAbility(
                new RosheenRoaringProphetManaEffect(),
                new TapSourceCost()
        );
        ability.setUndoPossible(false);
        this.addAbility(ability);
    }

    private RosheenRoaringProphet(final RosheenRoaringProphet card) {
        super(card);
    }

    @Override
    public RosheenRoaringProphet copy() {
        return new RosheenRoaringProphet(this);
    }
}

// Mixed from [Metalworker] and [Elementalist's Palette]
class RosheenRoaringProphetManaEffect extends ManaEffect {

    private final ConditionalManaBuilder manaBuilder = new RosheenRoaringProphetManaBuilder();
    private static final FilterCard filter = new FilterCard("a card with {X} in its mana cost");

    static {
        filter.add(VariableManaCostPredicate.instance);
    }

    RosheenRoaringProphetManaEffect() {
        super();
        staticText = "reveal any number of cards with {X} in their mana cost in your hand. "
                + "Add {C}{C} for each card revealed this way. Spend this mana only on costs that contain {X}";
    }

    private RosheenRoaringProphetManaEffect(final RosheenRoaringProphetManaEffect effect) {
        super(effect);
    }

    @Override
    public RosheenRoaringProphetManaEffect copy() {
        return new RosheenRoaringProphetManaEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        if (game != null) {
            Player controller = getPlayer(game, source);
            if (controller != null) {
                int maxAmount = controller.getHand().count(filter, game);
                if (maxAmount > 0) {
                    netMana.add(
                            manaBuilder.setMana(
                                    Mana.ColorlessMana(2 * maxAmount), source, game
                            ).build()
                    );
                }
            }
        }
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }
        Player controller = getPlayer(game, source);
        if (controller == null) {
            return mana;
        }
        int maxAmount = controller.getHand().count(filter, game);
        if (maxAmount > 0) {
            TargetCardInHand target = new TargetCardInHand(0, Integer.MAX_VALUE, filter);
            if (controller.choose(Outcome.Benefit, target, source, game)) {
                Cards cards = new CardsImpl(target.getTargets());
                controller.revealCards(source, cards, game);
                return manaBuilder.setMana(
                        Mana.ColorlessMana(target.getTargets().size() * 2), source, game
                ).build();
            }
        }
        return mana;
    }
}

class RosheenRoaringProphetManaBuilder extends ConditionalManaBuilder {
    @Override
    public ConditionalMana build(Object... options) {
        return new RosheenRoaringProphetConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only on costs that contain {X}";
    }
}

class RosheenRoaringProphetConditionalMana extends ConditionalMana {

    RosheenRoaringProphetConditionalMana(Mana mana) {
        super(mana);
        addCondition(new XCostManaCondition());
    }
}
