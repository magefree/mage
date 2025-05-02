package mage.cards.d;

import mage.MageInt;
import mage.abilities.Mode;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DepthDefiler extends CardImpl {

    public DepthDefiler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Kicker {C}
        this.addAbility(new KickerAbility("{C}"));

        // When you cast this spell, choose one. If it was kicked, choose both instead.
        // * Return target creature to its owner's hand.
        // * Target player draws two cards, then discards a card.
        this.addAbility(new DepthDefilerTriggeredAbility());
    }

    private DepthDefiler(final DepthDefiler card) {
        super(card);
    }

    @Override
    public DepthDefiler copy() {
        return new DepthDefiler(this);
    }
}

class DepthDefilerTriggeredAbility extends CastSourceTriggeredAbility {

    DepthDefilerTriggeredAbility() {
        super(new ReturnToHandTargetEffect(), false);
        this.addTarget(new TargetCreaturePermanent());
        Mode mode = new Mode(new DrawCardTargetEffect(2));
        mode.addEffect(new DiscardTargetEffect(1).setText(", then discards a card"));
        mode.addTarget(new TargetPlayer());
        this.addMode(mode);
        this.getModes().setChooseText(
                "choose one. If it was kicked, choose both instead."
        );
    }

    private DepthDefilerTriggeredAbility(final DepthDefilerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DepthDefilerTriggeredAbility copy() {
        return new DepthDefilerTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        int modes = KickedCondition.ONCE.apply(game, this) ? 2 : 1;
        this.getModes().setMinModes(modes);
        this.getModes().setMaxModes(modes);
        return true;
    }
}
