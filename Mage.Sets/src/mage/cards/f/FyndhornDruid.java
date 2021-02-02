
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.WasBlockedThisTurnWatcher;

/**
 *
 * @author L_J
 */
public final class FyndhornDruid extends CardImpl {

    public FyndhornDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Fyndhorn Druid dies, if it was blocked this turn, you gain 4 life.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new DiesSourceTriggeredAbility(new GainLifeEffect(4)), new SourceWasBlockedThisTurnCondition(),
                "When {this} dies, if it was blocked this turn, you gain 4 life."), new WasBlockedThisTurnWatcher());
    }

    private FyndhornDruid(final FyndhornDruid card) {
        super(card);
    }

    @Override
    public FyndhornDruid copy() {
        return new FyndhornDruid(this);
    }
}

class SourceWasBlockedThisTurnCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        WasBlockedThisTurnWatcher watcher = game.getState().getWatcher(WasBlockedThisTurnWatcher.class);
        return sourcePermanent != null && watcher != null && watcher.getWasBlockedThisTurnCreatures().contains(new MageObjectReference(sourcePermanent, game));
    }
}
