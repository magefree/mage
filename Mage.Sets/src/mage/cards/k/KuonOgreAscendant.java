
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.TokenImpl;
import mage.watchers.common.CreaturesDiedWatcher;

/**
 *
 * @author LevelX2
 */
public final class KuonOgreAscendant extends CardImpl {

    public KuonOgreAscendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        this.flipCard = true;
        this.flipCardName = "Kuon's Essence";

        // At the beginning of the end step, if three or more creatures died this turn, flip Kuon, Ogre Ascendant.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.NEXT, new FlipSourceEffect(new KuonsEssenceToken()),
                false, KuonOgreAscendantCondition.instance));
    }

    private KuonOgreAscendant(final KuonOgreAscendant card) {
        super(card);
    }

    @Override
    public KuonOgreAscendant copy() {
        return new KuonOgreAscendant(this);
    }
}

class KuonsEssenceToken extends TokenImpl {

    KuonsEssenceToken() {
        super("Kuon's Essence", "");
        this.supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.ENCHANTMENT);

        color.setBlack(true);

        // At the beginning of each player's upkeep, that player sacrifices a creature..
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                TargetController.ANY, new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "that player"),
                false));
    }
    private KuonsEssenceToken(final KuonsEssenceToken token) {
        super(token);
    }

    public KuonsEssenceToken copy() {
        return new KuonsEssenceToken(this);
    }
}

enum KuonOgreAscendantCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        CreaturesDiedWatcher watcher = game.getState().getWatcher(CreaturesDiedWatcher.class);
        if (watcher != null) {
            return watcher.getAmountOfCreaturesDiedThisTurn() > 2;
        }
        return false;
    }

    @Override
    public String toString() {
        return "if three or more creatures died this turn";
    }

}
