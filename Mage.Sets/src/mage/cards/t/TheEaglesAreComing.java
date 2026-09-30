package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.BirdSoldier44Token;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ConditionalTargetAdjuster;

import java.util.UUID;

public final class TheEaglesAreComing extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you own");
    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public TheEaglesAreComing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Kicker {2}{W}{W}
        this.addAbility(new KickerAbility("{2}{W}{W}"));

        // Choose target creature you own. If this spell was kicked, instead choose any number of target creatures you own.
        this.getSpellAbility().setTargetAdjuster(new ConditionalTargetAdjuster(
                KickedCondition.ONCE,
                new TargetPermanent(filter),
                new TargetPermanent(0, Integer.MAX_VALUE, filter)
        ));

        // Return each chosen creature to your hand.
        // At the beginning of the next upkeep, create a 4/4 white Bird Soldier creature token with flying for each creature returned to your hand this way.
        this.getSpellAbility().addEffect(new TheEaglesAreComingEffect());
    }

    private TheEaglesAreComing(final TheEaglesAreComing card) {
        super(card);
    }

    @Override
    public TheEaglesAreComing copy() {
        return new TheEaglesAreComing(this);
    }
}

class TheEaglesAreComingEffect extends ReturnToHandTargetEffect {
    TheEaglesAreComingEffect() {
        this.staticText = "Choose target creature you own. " +
            "If this spell was kicked, instead choose any number of target creatures you own. " +
            "Return each chosen creature to your hand. " +
            "At the beginning of the next upkeep, create a 4/4 white Bird Soldier creature token with flying for each creature returned to your hand this way.";
    }

    private TheEaglesAreComingEffect(final TheEaglesAreComingEffect effect) {
        super(effect);
    }

    @Override
    public TheEaglesAreComingEffect copy() {
        return new TheEaglesAreComingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return super.apply(game, source) &&
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new CreateTokenEffect(new BirdSoldier44Token(), this.getTargetPointer().getTargets(game, source).size())), source) != null;
    }
}
