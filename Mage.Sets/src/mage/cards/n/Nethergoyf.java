package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessPlusOneSourceEffect;
import mage.abilities.hint.HintUtils;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.awt.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class Nethergoyf extends CardImpl {

    private static final DynamicValue powerValue = CardTypesInGraveyardCount.YOU;

    public Nethergoyf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.LHURGOYF);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Nethergoyf's power is equal to the number of card types among cards in your graveyard and its toughness is equal to that number plus 1.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessPlusOneSourceEffect(powerValue)));

        // Escape--{2}{B}, Exile any number of other cards from your graveyard with four or more card types among them.
        CostsImpl<Cost> additionalCost = new CostsImpl();
        additionalCost.add(new ExileFromGraveCost(
                new NethergoyfTarget(),
                "exile any number of other cards from your graveyard with four or more card types among them")
        );
        this.addAbility(new EscapeAbility(this, "{2}{B}", additionalCost));
    }

    private Nethergoyf(final Nethergoyf card) {
        super(card);
    }

    @Override
    public Nethergoyf copy() {
        return new Nethergoyf(this);
    }
}

class NethergoyfTarget extends TargetCardInYourGraveyard {

    private static final FilterCard filter = new FilterCard("other cards from your graveyard with four or more card types among them");

    static {
        filter.add(AnotherPredicate.instance);
    }

    NethergoyfTarget() {
        super(1, Integer.MAX_VALUE, filter, true);
    }

    private NethergoyfTarget(final NethergoyfTarget target) {
        super(target);
    }

    @Override
    public NethergoyfTarget copy() {
        return new NethergoyfTarget(this);
    }

    @Override
    public boolean isChosen(Game game) {
        return super.isChosen(game) && metCondition(this.getTargets(), game);
    }

    @Override
    public String getMessage(Game game) {
        String text = "Select " + CardUtil.addArticle(targetName);
        Set<CardType> types = typesAmongSelection(this.getTargets(), game);
        text += " (selected " + this.getTargets().size() + " cards; card types: ";
        text += HintUtils.prepareText(
                types.size() + " of 4",
                types.size() >= 4 ? Color.GREEN : Color.RED
        );
        text += " [" + types.stream().map(CardType::toString).collect(Collectors.joining(", ")) + "])";
        return text;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        if (!super.canChoose(sourceControllerId, source, game)) {
            return false;
        }
        // Check that exiling all the possible cards would have >= 4 different card types
        return metCondition(this.possibleTargets(sourceControllerId, source, game), game);
    }

    private static Set<CardType> typesAmongSelection(Collection<UUID> cardsIds, Game game) {
        return cardsIds
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .flatMap(c -> c.getCardType(game).stream())
                .collect(Collectors.toSet());
    }

    private static boolean metCondition(Collection<UUID> cardsIds, Game game) {
        return typesAmongSelection(cardsIds, game).size() >= 4;
    }
}