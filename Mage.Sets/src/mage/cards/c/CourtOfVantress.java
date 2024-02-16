package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CourtOfVantress extends CardImpl {

    private static final FilterPermanent filter
            = new FilterArtifactOrEnchantmentPermanent("other target enchantment or artifact");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public CourtOfVantress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        // When Court of Vantress enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect()).addHint(MonarchHint.instance));

        // At the beginning of your upkeep, choose up to one other target enchantment or artifact. If you're the monarch, you may create a token that's a copy of it. If you're not the monarch, you may have Court of Vantress become a copy of it, except it has this ability.
        this.addAbility(makeAbility());
    }

    private CourtOfVantress(final CourtOfVantress card) {
        super(card);
    }

    @Override
    public CourtOfVantress copy() {
        return new CourtOfVantress(this);
    }

    static Ability makeAbility() {
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new CourtOfVantressEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetPermanent(0, 1, filter));
        return ability;
    }
}

class CourtOfVantressEffect extends OneShotEffect {

    private static final CopyApplier applier = new CopyApplier() {
        @Override
        public boolean apply(Game game, MageObject blueprint, Ability source, UUID targetObjectId) {
            blueprint.getAbilities().add(CourtOfVantress.makeAbility());
            return true;
        }
    };

    CourtOfVantressEffect() {
        super(Outcome.Benefit);
        staticText = "choose up to one other target enchantment or artifact. If you're the monarch, " +
                "you may create a token that's a copy of it. If you're not the monarch, " +
                "you may have {this} become a copy of it, except it has this ability";
    }

    private CourtOfVantressEffect(final CourtOfVantressEffect effect) {
        super(effect);
    }

    @Override
    public CourtOfVantressEffect copy() {
        return new CourtOfVantressEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        if (source.isControlledBy(game.getMonarchId())) {
            return player.chooseUse(outcome, "Create a token copy of " + permanent.getIdName() + '?', source, game)
                    && new CreateTokenCopyTargetEffect().setSavedPermanent(permanent).apply(game, source);
        }
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (sourcePermanent == null || !player.chooseUse(outcome, "Have " + sourcePermanent.getIdName() +
                " become a copy of " + permanent.getIdName() + '?', source, game)) {
            return false;
        }
        game.copyPermanent(Duration.Custom, permanent, sourcePermanent.getId(), source, applier);
        return true;
    }
}
