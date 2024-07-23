package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReptilianRecruiter extends CardImpl {

    public ReptilianRecruiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Reptilian Recruiter enters, choose target creature. If that creature's power is 2 or less or if you control another Lizard, gain control of that creature until end of turn, untap it, and it gains haste until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReptilianRecruiterEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ReptilianRecruiter(final ReptilianRecruiter card) {
        super(card);
    }

    @Override
    public ReptilianRecruiter copy() {
        return new ReptilianRecruiter(this);
    }
}

class ReptilianRecruiterEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.LIZARD);

    static {
        filter.add(AnotherPredicate.instance);
    }

    ReptilianRecruiterEffect() {
        super(Outcome.Benefit);
        staticText = "choose target creature. If that creature's power is 2 or less or if you control another Lizard, " +
                "gain control of that creature until end of turn, untap it, and it gains haste until end of turn";
    }

    private ReptilianRecruiterEffect(final ReptilianRecruiterEffect effect) {
        super(effect);
    }

    @Override
    public ReptilianRecruiterEffect copy() {
        return new ReptilianRecruiterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null
                || permanent.getPower().getValue() > 2
                && !game.getBattlefield().contains(filter, source, game, 1)) {
            return false;
        }
        game.addEffect(new GainControlTargetEffect(Duration.EndOfTurn)
                .setTargetPointer(new FixedTarget(permanent, game)), source);
        game.processAction();
        permanent.untap(game);
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}
