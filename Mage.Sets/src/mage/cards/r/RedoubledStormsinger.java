package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTargets;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RedoubledStormsinger extends CardImpl {

    public RedoubledStormsinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever this creature attacks, for each creature token you control that entered this turn, create a tapped and attacking token that's a copy of that token. At the beginning of the next end step, sacrifice those tokens.
        this.addAbility(new AttacksTriggeredAbility(new RedoubledStormsingerEffect()));
    }

    private RedoubledStormsinger(final RedoubledStormsinger card) {
        super(card);
    }

    @Override
    public RedoubledStormsinger copy() {
        return new RedoubledStormsinger(this);
    }
}

class RedoubledStormsingerEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(TokenPredicate.TRUE);
        filter.add(EnteredThisTurnPredicate.instance);
    }

    RedoubledStormsingerEffect() {
        super(Outcome.Benefit);
        staticText = "for each creature token you control that entered this turn, " +
                "create a tapped and attacking token that's a copy of that token. " +
                "At the beginning of the next end step, sacrifice those tokens";
    }

    private RedoubledStormsingerEffect(final RedoubledStormsingerEffect effect) {
        super(effect);
    }

    @Override
    public RedoubledStormsingerEffect copy() {
        return new RedoubledStormsingerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<Permanent> addedTokens = new HashSet<>();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        )) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                    null, null, false,
                    1, true, true
            ).setSavedPermanent(permanent);
            effect.apply(game, source);
            addedTokens.addAll(effect.getAddedPermanents());
        }
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new SacrificeTargetEffect().setTargetPointer(new FixedTargets(addedTokens, game))
        ), source);
        return true;
    }
}
