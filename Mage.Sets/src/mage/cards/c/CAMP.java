package mage.cards.c;

import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FortifyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.TappedForManaEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.JunkToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CAMP extends CardImpl {

    public CAMP(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.FORTIFICATION);

        // Whenever fortified land is tapped for mana, put a +1/+1 counter on target creature you control. If that creature shares a color with the mana that land produced, create a Junk token.
        this.addAbility(new CAMPTriggeredAbility());

        // Fortify {3}
        this.addAbility(new FortifyAbility(3));
    }

    private CAMP(final CAMP card) {
        super(card);
    }

    @Override
    public CAMP copy() {
        return new CAMP(this);
    }
}


class CAMPTriggeredAbility extends TriggeredAbilityImpl {

    CAMPTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
        addTarget(new TargetControlledCreaturePermanent());
    }

    private CAMPTriggeredAbility(final CAMPTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CAMPTriggeredAbility copy() {
        return new CAMPTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent fortification = game.getPermanent(getSourceId());
        if (fortification == null || !event.getSourceId().equals(fortification.getAttachedTo())) {
            return false;
        }
        TappedForManaEvent mEvent = (TappedForManaEvent) event;
        Mana mana = mEvent.getMana();
        getEffects().clear();
        getEffects().add(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        getEffects().add(new CAMPEffect(mana));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever fortified land is tapped for mana, put a +1/+1 counter on target creature you control. "
                + "If that creature shares a color with the mana that land produced, create a Junk token.";
    }
}

class CAMPEffect extends OneShotEffect {

    private final Mana mana;

    CAMPEffect(Mana mana) {
        super(Outcome.Benefit);
        this.mana = mana.copy();
    }

    private CAMPEffect(final CAMPEffect effect) {
        super(effect);
        this.mana = effect.mana;
    }

    @Override
    public CAMPEffect copy() {
        return new CAMPEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (target == null) {
            return false;
        }
        ObjectColor targetColor = target.getColor(game);
        if (mana.getWhite() > 0 && targetColor.isWhite()
                || (mana.getBlue() > 0 && targetColor.isBlue())
                || (mana.getBlack() > 0 && targetColor.isBlack())
                || (mana.getRed() > 0 && targetColor.isRed())
                || (mana.getGreen() > 0 && targetColor.isGreen())
        ) {
            return new CreateTokenEffect(new JunkToken())
                    .apply(game, source);
        }
        return false;
    }

}