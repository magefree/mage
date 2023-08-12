
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public final class DragonlordDromoka extends CardImpl {

    public DragonlordDromoka(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Dragonlord Dromoka can't be countered
        this.addAbility(new CantBeCounteredSourceAbility());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
        // Your opponents can't cast spells during your turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DragonlordDromokaEffect()));
        
    }

    private DragonlordDromoka(final DragonlordDromoka card) {
        super(card);
    }

    @Override
    public DragonlordDromoka copy() {
        return new DragonlordDromoka(this);
    }
}

class DragonlordDromokaEffect extends ContinuousRuleModifyingEffectImpl {

    public DragonlordDromokaEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Your opponents can't cast spells during your turn";
    }

    public DragonlordDromokaEffect(final DragonlordDromokaEffect effect) {
        super(effect);
    }

    @Override
    public DragonlordDromokaEffect copy() {
        return new DragonlordDromokaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.isActivePlayer(source.getControllerId()) &&
                game.getPlayer(source.getControllerId()).hasOpponent(event.getPlayerId(), game)) {
            return true;
        }
        return false;
    }
}
