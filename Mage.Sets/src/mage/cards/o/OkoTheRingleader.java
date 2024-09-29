package mage.cards.o;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.common.CommittedCrimeCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ElkToken;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.functions.CopyApplier;
import mage.watchers.common.CommittedCrimeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OkoTheRingleader extends CardImpl {

    public OkoTheRingleader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OKO);
        this.setStartingLoyalty(3);

        // At the beginning of combat on your turn, Oko, the Ringleader becomes a copy of up to one target creature you control until end of turn, except he has hexproof.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new OkoTheRingleaderCopySelfEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1));
        this.addAbility(ability);

        // +1: Draw two cards. If you've committed a crime this turn, discard a card. Otherwise, discard two cards.
        ability = new LoyaltyAbility(new DrawCardSourceControllerEffect(2), 1);
        ability.addEffect(new ConditionalOneShotEffect(
                new DiscardControllerEffect(1), new DiscardControllerEffect(2),
                CommittedCrimeCondition.instance, "if you've committed a crime this turn, " +
                "discard a card. Otherwise, discard two cards"
        ));
        this.addAbility(ability.addHint(CommittedCrimeCondition.getHint()), new CommittedCrimeWatcher());

        // -1: Create a 3/3 green Elk creature token.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new ElkToken()), -1));

        // -5: For each other nonland permanent you control, create a token that's a copy of that permanent.
        this.addAbility(new LoyaltyAbility(new OkoTheRingleaderCopyTokenEffect(), -5));
    }

    private OkoTheRingleader(final OkoTheRingleader card) {
        super(card);
    }

    @Override
    public OkoTheRingleader copy() {
        return new OkoTheRingleader(this);
    }
}

class OkoTheRingleaderCopySelfEffect extends OneShotEffect {

    private static final CopyApplier applier = new CopyApplier() {
        @Override
        public boolean apply(Game game, MageObject blueprint, Ability source, UUID targetObjectId) {
            blueprint.getAbilities().add(HexproofAbility.getInstance());
            return true;
        }
    };

    OkoTheRingleaderCopySelfEffect() {
        super(Outcome.Benefit);
        staticText = "{this} becomes a copy of up to one target creature " +
                "you control until end of turn, except he has hexproof";
    }

    private OkoTheRingleaderCopySelfEffect(final OkoTheRingleaderCopySelfEffect effect) {
        super(effect);
    }

    @Override
    public OkoTheRingleaderCopySelfEffect copy() {
        return new OkoTheRingleaderCopySelfEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || creature == null) {
            return false;
        }
        game.copyPermanent(Duration.EndOfTurn, creature, permanent.getId(), source, applier);
        return true;
    }
}

class OkoTheRingleaderCopyTokenEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterNonlandPermanent();

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    OkoTheRingleaderCopyTokenEffect() {
        super(Outcome.Benefit);
        staticText = "for each other nonland permanent you control, create a token that's a copy of that permanent";
    }

    private OkoTheRingleaderCopyTokenEffect(final OkoTheRingleaderCopyTokenEffect effect) {
        super(effect);
    }

    @Override
    public OkoTheRingleaderCopyTokenEffect copy() {
        return new OkoTheRingleaderCopyTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            new CreateTokenCopyTargetEffect().setSavedPermanent(permanent).apply(game, source);
        }
        return true;
    }
}
