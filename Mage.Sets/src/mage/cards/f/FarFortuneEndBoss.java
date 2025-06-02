package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FarFortuneEndBoss extends CardImpl {

    public FarFortuneEndBoss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // Whenever you attack, Far Fortune deals 1 damage to each opponent.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT), 1
        ));

        // Max speed -- If a source you control would deal damage to an opponent or a permanent an opponent controls, it deals that much damage plus 1 instead.
        this.addAbility(new MaxSpeedAbility(new FarFortuneEndBossEffect()));
    }

    private FarFortuneEndBoss(final FarFortuneEndBoss card) {
        super(card);
    }

    @Override
    public FarFortuneEndBoss copy() {
        return new FarFortuneEndBoss(this);
    }
}

class FarFortuneEndBossEffect extends ReplacementEffectImpl {

    FarFortuneEndBossEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if a source you control would deal damage to an opponent " +
                "or a permanent an opponent controls, it deals that much damage plus 1 instead";
    }

    private FarFortuneEndBossEffect(final FarFortuneEndBossEffect effect) {
        super(effect);
    }

    @Override
    public FarFortuneEndBossEffect copy() {
        return new FarFortuneEndBossEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PERMANENT:
            case DAMAGE_PLAYER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!source.isControlledBy(game.getControllerId(event.getSourceId()))) {
            return false;
        }
        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        return opponents.contains(event.getTargetId())
                && opponents.contains(game.getControllerId(event.getTargetId()));
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 1));
        return false;
    }
}
