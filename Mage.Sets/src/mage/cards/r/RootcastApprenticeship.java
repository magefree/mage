package mage.cards.r;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SquirrelToken;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RootcastApprenticeship extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("token you control");
    private static final FilterPermanent filter2 = new FilterArtifactPermanent("a nontoken artifact");

    static {
        filter.add(TokenPredicate.TRUE);
        filter2.add(TokenPredicate.FALSE);
    }

    public RootcastApprenticeship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Choose three. You may choose the same mode more than once.
        this.getSpellAbility().getModes().setMinModes(3);
        this.getSpellAbility().getModes().setMaxModes(3);
        this.getSpellAbility().getModes().setMayChooseSameModeMoreThanOnce(true);

        // * Put two +1/+1 counters on target creature.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // * Create a token that's a copy of target token you control.
        this.getSpellAbility().addMode(new Mode(new CreateTokenCopyTargetEffect()).addTarget(new TargetPermanent(filter)));

        // * Target player creates a 1/1 green Squirrel creature token.
        this.getSpellAbility().addMode(new Mode(new CreateTokenTargetEffect(new SquirrelToken())).addTarget(new TargetPlayer()));

        // * Target opponent sacrifices a nontoken artifact.
        this.getSpellAbility().addMode(new Mode(new SacrificeEffect(filter2, 1, "target opponent")).addTarget(new TargetOpponent()));
    }

    private RootcastApprenticeship(final RootcastApprenticeship card) {
        super(card);
    }

    @Override
    public RootcastApprenticeship copy() {
        return new RootcastApprenticeship(this);
    }
}
