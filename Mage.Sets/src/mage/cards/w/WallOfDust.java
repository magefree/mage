package mage.cards.w;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BlocksTriggeredAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author L_J (based on LevelX2)
 */
public final class WallOfDust extends CardImpl {

    public WallOfDust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Whenever Wall of Dust blocks a creature, that creature can't attack during its controller's next turn.
        this.addAbility(new BlocksTriggeredAbility(new WallOfDustRestrictionEffect(), false, true));
    }

    private WallOfDust(final WallOfDust card) {
        super(card);
    }

    @Override
    public WallOfDust copy() {
        return new WallOfDust(this);
    }
}

class WallOfDustRestrictionEffect extends RestrictionEffect {

    protected MageObjectReference targetPermanentReference;

    public WallOfDustRestrictionEffect() {
        super(Duration.Custom);
        staticText = "that creature can't attack during its controller's next turn";
    }

    public WallOfDustRestrictionEffect(final WallOfDustRestrictionEffect effect) {
        super(effect);
        this.targetPermanentReference = effect.targetPermanentReference;
    }

    @Override
    public WallOfDustRestrictionEffect copy() {
        return new WallOfDustRestrictionEffect(this);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (targetPermanentReference == null || targetPermanentReference.getPermanent(game) == null) {
            return true;
        }

        return game.getPhase().getType() == TurnPhase.END && this.isYourNextTurn(game);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Permanent perm = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (perm != null) {
            targetPermanentReference = new MageObjectReference(perm, game);
            setStartingControllerAndTurnNum(game, perm.getControllerId(), game.getActivePlayerId());
        } else {
            discard();
        }
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(getTargetPointer().getFirst(game, source))) {
            return game.isActivePlayer(permanent.getControllerId());
        }
        return false;
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }
}
