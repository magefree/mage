package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.FaceVillainousChoice;
import mage.choices.VillainousChoice;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HuntedByTheFamily extends CardImpl {

    public HuntedByTheFamily(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}{U}");

        // Choose up to four target creatures you don't control. For each of them, that creature's controller faces a villainous choice -- That creature becomes a 1/1 white Human creature and loses all abilities, or you create a token that's a copy of it.
        this.getSpellAbility().addEffect(new HuntedByTheFamilyEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(
                0, 4, StaticFilters.FILTER_CREATURES_YOU_DONT_CONTROL
        ));
    }

    private HuntedByTheFamily(final HuntedByTheFamily card) {
        super(card);
    }

    @Override
    public HuntedByTheFamily copy() {
        return new HuntedByTheFamily(this);
    }
}

class HuntedByTheFamilyEffect extends OneShotEffect {

    HuntedByTheFamilyEffect() {
        super(Outcome.Benefit);
        staticText = "choose up to four target creatures you don't control. " +
                "For each of them, that creature's controller faces a villainous choice &mdash; " +
                "That creature becomes a 1/1 white Human creature and loses all abilities, " +
                "or you create a token that's a copy of it";
    }

    private HuntedByTheFamilyEffect(final HuntedByTheFamilyEffect effect) {
        super(effect);
    }

    @Override
    public HuntedByTheFamilyEffect copy() {
        return new HuntedByTheFamilyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            Player player = game.getPlayer(game.getControllerId(targetId));
            if (permanent == null || player == null) {
                continue;
            }
            FaceVillainousChoice choice = new FaceVillainousChoice(
                    Outcome.DestroyPermanent,
                    new HuntedByTheFamilyFirstChoice(permanent),
                    new HuntedByTheFamilySecondChoice(permanent)
            );
            choice.faceChoice(player, game, source);
        }
        return true;
    }
}

class HuntedByTheFamilyFirstChoice extends VillainousChoice {

    private final Permanent permanent;

    HuntedByTheFamilyFirstChoice(Permanent permanent) {
        super("", permanent.getIdName() + " becomes a 1/1 white Human and loses all abilities");
        this.permanent = permanent;
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        game.addEffect(new BecomesCreatureTargetEffect(
                new CreatureToken(1, 1)
                        .withSubType(SubType.HUMAN)
                        .withColor("W"),
                true, false, Duration.Custom
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}

class HuntedByTheFamilySecondChoice extends VillainousChoice {

    private final Permanent permanent;

    HuntedByTheFamilySecondChoice(Permanent permanent) {
        super("", "{controller} creates a token that's a copy of " + permanent.getIdName());
        this.permanent = permanent;
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        return new CreateTokenCopyTargetEffect().setSavedPermanent(permanent).apply(game, source);
    }
}
