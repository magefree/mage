package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
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
public final class LaquatussChampion extends CardImpl {

    public LaquatussChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(3);

        // When Laquatus's Champion enters the battlefield, target player loses 6 life.
        Ability ability = new LaquatussChampionEntersBattlefieldTriggeredAbility(new LoseLifeTargetEffect(6));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        // When Laquatus's Champion leaves the battlefield, that player gains 6 life.
        this.addAbility(new LaquatussChampionLeavesBattlefieldTriggeredAbility());
        // {B}: Regenerate Laquatus's Champion.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{B}")));
    }

    private LaquatussChampion(final LaquatussChampion card) {
        super(card);
    }

    @Override
    public LaquatussChampion copy() {
        return new LaquatussChampion(this);
    }
}

class LaquatussChampionEntersBattlefieldTriggeredAbility extends EntersBattlefieldTriggeredAbility {

    public LaquatussChampionEntersBattlefieldTriggeredAbility(Effect effect) {
        super(effect, false);
    }

    public LaquatussChampionEntersBattlefieldTriggeredAbility(final LaquatussChampionEntersBattlefieldTriggeredAbility ability) {
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
    public LaquatussChampionEntersBattlefieldTriggeredAbility copy() {
        return new LaquatussChampionEntersBattlefieldTriggeredAbility(this);
    }
}

class LaquatussChampionLeavesBattlefieldTriggeredAbility extends LeavesBattlefieldTriggeredAbility {

    public LaquatussChampionLeavesBattlefieldTriggeredAbility() {
        super(new GainLifeTargetEffect(6), false);
    }

    public LaquatussChampionLeavesBattlefieldTriggeredAbility(LaquatussChampionLeavesBattlefieldTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            String key = CardUtil.getCardZoneString("targetPlayer", this.getSourceId(), game, true);
            Object object = game.getState().getValue(key);
            if (object instanceof UUID) {
                this.getEffects().setTargetPointer(new FixedTarget((UUID) object));
                return true;
            }
        }
        return false;
    }

    @Override
    public LaquatussChampionLeavesBattlefieldTriggeredAbility copy() {
        return new LaquatussChampionLeavesBattlefieldTriggeredAbility(this);
    }
}
