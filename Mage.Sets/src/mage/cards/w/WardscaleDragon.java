
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class WardscaleDragon extends CardImpl {

    public WardscaleDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As long as Wardscale Dragon is attacking, defending player can't cast spells.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WardscaleDragonRuleEffect()));

    }

    private WardscaleDragon(final WardscaleDragon card) {
        super(card);
    }

    @Override
    public WardscaleDragon copy() {
        return new WardscaleDragon(this);
    }
}

class WardscaleDragonRuleEffect extends ContinuousRuleModifyingEffectImpl {

    public WardscaleDragonRuleEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, true, false);
        staticText = "As long as {this} is attacking, defending player can't cast spells";
    }

    private WardscaleDragonRuleEffect(final WardscaleDragonRuleEffect effect) {
        super(effect);
    }

    @Override
    public WardscaleDragonRuleEffect copy() {
        return new WardscaleDragonRuleEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null && sourcePermanent.isAttacking()) {
            return event.getPlayerId().equals(game.getCombat().getDefendingPlayerId(sourcePermanent.getId(), game));
        }
        return false;
    }
}
