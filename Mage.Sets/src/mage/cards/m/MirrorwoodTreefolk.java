
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2 & L_J
 */
public final class MirrorwoodTreefolk extends CardImpl {

    public MirrorwoodTreefolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {2}{R}{W}: The next time damage would be dealt to Mirrorwood Treefolk this turn, that damage is dealt to any target instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MirrorwoodTreefolkEffect(), new ManaCostsImpl<>("{2}{R}{W}"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private MirrorwoodTreefolk(final MirrorwoodTreefolk card) {
        super(card);
    }

    @Override
    public MirrorwoodTreefolk copy() {
        return new MirrorwoodTreefolk(this);
    }
}

class MirrorwoodTreefolkEffect extends RedirectionEffect {

    protected MageObjectReference redirectToObject;

    public MirrorwoodTreefolkEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, UsageType.ONE_USAGE_ABSOLUTE);
        staticText = "The next time damage would be dealt to {this} this turn, that damage is dealt to any target instead";
    }

    public MirrorwoodTreefolkEffect(final MirrorwoodTreefolkEffect effect) {
        super(effect);
        this.redirectToObject = effect.redirectToObject;
    }

    @Override
    public MirrorwoodTreefolkEffect copy() {
        return new MirrorwoodTreefolkEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        redirectToObject = new MageObjectReference(source.getTargets().get(0).getFirstTarget(), game);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())) {
            if (redirectToObject.equals(new MageObjectReference(source.getTargets().get(0).getFirstTarget(), game))) {
                redirectTarget = source.getTargets().get(0);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

}
