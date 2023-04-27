
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class VexingShusher extends CardImpl {

    public VexingShusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R/G}{R/G}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vexing Shusher can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());
        // {R/G}: Target spell can't be countered.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new VexingShusherCantCounterTargetEffect(), new ManaCostsImpl<>("{R/G}"));
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
    }

    private VexingShusher(final VexingShusher card) {
        super(card);
    }

    @Override
    public VexingShusher copy() {
        return new VexingShusher(this);
    }
}

class VexingShusherCantCounterTargetEffect extends ContinuousRuleModifyingEffectImpl {

    public VexingShusherCantCounterTargetEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Target spell can't be countered";
    }

    public VexingShusherCantCounterTargetEffect(final VexingShusherCantCounterTargetEffect effect) {
        super(effect);
    }

    @Override
    public VexingShusherCantCounterTargetEffect copy() {
        return new VexingShusherCantCounterTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null) {
            return "This spell can't be countered (" + sourceObject.getName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(targetPointer.getFirst(game, source));
    }

}
