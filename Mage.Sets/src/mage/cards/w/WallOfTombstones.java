
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetToughnessSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author L_J
 */
public final class WallOfTombstones extends CardImpl {

    public WallOfTombstones(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // At the beginning of your upkeep, change Wall of Tombstones’s base toughness to 1 plus the number of creature cards in your graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new WallOfTombstonesEffect(), TargetController.YOU, false));

    }

    public WallOfTombstones(final WallOfTombstones card) {
        super(card);
    }

    @Override
    public WallOfTombstones copy() {
        return new WallOfTombstones(this);
    }
}

class WallOfTombstonesEffect extends OneShotEffect {

    public WallOfTombstonesEffect() {
        super(Outcome.Detriment);
        this.staticText = "change {this}’s base toughness to 1 plus the number of creature cards in your graveyard";
    }

    public WallOfTombstonesEffect(final WallOfTombstonesEffect effect) {
        super(effect);
    }

    @Override
    public WallOfTombstonesEffect copy() {
        return new WallOfTombstonesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int newToughness = CardUtil.addWithOverflowCheck(1, new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE).calculate(game, source, this));
        game.addEffect(new SetToughnessSourceEffect(new StaticValue(newToughness), Duration.Custom, SubLayer.SetPT_7b), source);
        return true;
    }
}
