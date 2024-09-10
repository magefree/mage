package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.DisguiseAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FugitiveCodebreaker extends CardImpl {

    public FugitiveCodebreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Prowess
        this.addAbility(new ProwessAbility());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Disguise {5}{R}. This cost is reduced by {1} for each instant and sorcery card in your graveyard.
        this.addAbility(new FugitiveCodebreakerDisguiseAbility(this));

        // When Fugitive Codebreaker is turned face up, discard your hand, then draw three cards.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new DiscardHandControllerEffect());
        ability.addEffect(new DrawCardSourceControllerEffect(3).concatBy(", then"));
        this.addAbility(ability);
    }

    private FugitiveCodebreaker(final FugitiveCodebreaker card) {
        super(card);
    }

    @Override
    public FugitiveCodebreaker copy() {
        return new FugitiveCodebreaker(this);
    }
}

class FugitiveCodebreakerDisguiseAbility extends DisguiseAbility {

    static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD);
    private static final Hint hint = new ValueHint("instant and/or sorcery in your graveyard", xValue);

    FugitiveCodebreakerDisguiseAbility(Card card) {
        super(card, new ManaCostsImpl<>("{5}{R}"), FugitiveCodebreakerAdjuster.instance);
        addHint(hint);
    }

    private FugitiveCodebreakerDisguiseAbility(final FugitiveCodebreakerDisguiseAbility ability) {
        super(ability);
    }

    @Override
    public FugitiveCodebreakerDisguiseAbility copy() {
        return new FugitiveCodebreakerDisguiseAbility(this);
    }

    @Override
    public String getRule() {
        return "Disguise {5}{R}. This cost is reduced by {1} for each instant and sorcery card in your graveyard.";
    }
}

enum FugitiveCodebreakerAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        CardUtil.reduceCost(ability, FugitiveCodebreakerDisguiseAbility.xValue.calculate(game, ability, null));
    }
}
