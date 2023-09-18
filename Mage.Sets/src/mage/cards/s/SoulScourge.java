package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class SoulScourge extends CardImpl {

    public SoulScourge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Soul Scourge enters the battlefield, target player loses 3 life.
        Ability ability = new SoulScourgeEntersBattlefieldTriggeredAbility(new LoseLifeTargetEffect(3));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        // When Soul Scourge leaves the battlefield, that player gains 3 life.
        this.addAbility(new SoulScourgeLeavesBattlefieldTriggeredAbility());
    }

    private SoulScourge(final SoulScourge card) {
        super(card);
    }

    @Override
    public SoulScourge copy() {
        return new SoulScourge(this);
    }
}

class SoulScourgeEntersBattlefieldTriggeredAbility extends EntersBattlefieldTriggeredAbility {

    public SoulScourgeEntersBattlefieldTriggeredAbility(Effect effect) {
        super(effect, false);
    }

    private SoulScourgeEntersBattlefieldTriggeredAbility(final SoulScourgeEntersBattlefieldTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean activate(Game game, boolean noMana) {
        if (super.activate(game, noMana)) {
            Player player = game.getPlayer(getFirstTarget());
            if (player != null) {
                String key = CardUtil.getCardZoneString("targetPlayer", getSourceId(), game);
                game.getState().setValue(key, player.getId());
            }
            return true;
        }
        return false;
    }

    @Override
    public SoulScourgeEntersBattlefieldTriggeredAbility copy() {
        return new SoulScourgeEntersBattlefieldTriggeredAbility(this);
    }
}

class SoulScourgeLeavesBattlefieldTriggeredAbility extends LeavesBattlefieldTriggeredAbility {

    public SoulScourgeLeavesBattlefieldTriggeredAbility() {
        super(new GainLifeTargetEffect(3), false);
    }

    private SoulScourgeLeavesBattlefieldTriggeredAbility(final SoulScourgeLeavesBattlefieldTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            String key = CardUtil.getCardZoneString("targetPlayer", this.getSourceId(), game, true);
            Object object = game.getState().getValue(key);
            if (object instanceof UUID) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget((UUID) object));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public SoulScourgeLeavesBattlefieldTriggeredAbility copy() {
        return new SoulScourgeLeavesBattlefieldTriggeredAbility(this);
    }
}
