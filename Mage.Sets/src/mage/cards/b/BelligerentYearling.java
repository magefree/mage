package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BelligerentYearling extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.DINOSAUR, "another Dinosaur");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public BelligerentYearling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever another Dinosaur enters the battlefield under your control, you may have Belligerent Yearling's base power become equal to that creature's power until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new BelligerentYearlingEffect(), filter,
                true, SetTargetPointer.PERMANENT
        ));
    }

    private BelligerentYearling(final BelligerentYearling card) {
        super(card);
    }

    @Override
    public BelligerentYearling copy() {
        return new BelligerentYearling(this);
    }
}

/**
 * Inspired by {@link mage.cards.e.EldraziMimic}
 */
class BelligerentYearlingEffect extends OneShotEffect {

    public BelligerentYearlingEffect() {
        super(Outcome.Detriment);
        staticText = "have {this}'s base power become equal to that creature's power until end of turn";
    }

    private BelligerentYearlingEffect(final BelligerentYearlingEffect effect) {
        super(effect);
    }

    @Override
    public BelligerentYearlingEffect copy() {
        return new BelligerentYearlingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (controller == null || permanent == null) {
            return false;
        }

        ContinuousEffect effect = new SetBasePowerSourceEffect(permanent.getPower().getValue(), Duration.EndOfTurn);
        game.addEffect(effect, source);
        return true;
    }
}
