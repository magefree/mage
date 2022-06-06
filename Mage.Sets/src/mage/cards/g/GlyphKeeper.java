
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.EmbalmAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.NumberOfTimesPermanentTargetedATurnWatcher;

/**
 *
 * @author Styxo
 */
public final class GlyphKeeper extends CardImpl {

    public GlyphKeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Glyph Keeper becomes the target of a spell or ability for the first time each turn, counter that spell or ability.
        this.addAbility(new GlyphKeeperAbility(), new NumberOfTimesPermanentTargetedATurnWatcher());

        // Embalm {5}{U}{U}
        this.addAbility(new EmbalmAbility(new ManaCostsImpl<>("{5}{U}{U}"), this));

    }

    private GlyphKeeper(final GlyphKeeper card) {
        super(card);
    }

    @Override
    public GlyphKeeper copy() {
        return new GlyphKeeper(this);
    }
}

class GlyphKeeperAbility extends TriggeredAbilityImpl {

    public GlyphKeeperAbility() {
        super(Zone.BATTLEFIELD, new CounterTargetEffect(), false);
    }

    public GlyphKeeperAbility(final GlyphKeeperAbility ability) {
        super(ability);
    }

    @Override
    public GlyphKeeperAbility copy() {
        return new GlyphKeeperAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.isCreature(game)) {
                NumberOfTimesPermanentTargetedATurnWatcher watcher = game.getState().getWatcher(NumberOfTimesPermanentTargetedATurnWatcher.class);
                if (watcher != null
                        && watcher.notMoreThanOnceTargetedThisTurn(permanent, game)) {
                    for (Effect effect : getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getSourceId()));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} becomes the target of a spell or ability for the first time each turn, counter that spell or ability.";
    }

}