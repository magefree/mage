package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWhileSaddledTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.SaddleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.SaddledSourceThisTurnPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.watchers.common.SaddledMountWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CalamityGallopingInferno extends CardImpl {

    public CalamityGallopingInferno(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HORSE);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Calamity, Galloping Inferno attacks while saddled, choose a nonlegendary creature that saddled it this turn and create a tapped and attacking token that's a copy of it. Sacrifice that token at the beginning of the next end step. Repeat this process once.
        this.addAbility(new AttacksWhileSaddledTriggeredAbility(new CalamityGallopingInfernoEffect()), new SaddledMountWatcher());

        // Saddle 1
        this.addAbility(new SaddleAbility(1));
    }

    private CalamityGallopingInferno(final CalamityGallopingInferno card) {
        super(card);
    }

    @Override
    public CalamityGallopingInferno copy() {
        return new CalamityGallopingInferno(this);
    }
}

class CalamityGallopingInfernoEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("nonlegendary creature that saddled it this turn");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
        filter.add(SaddledSourceThisTurnPredicate.instance);
    }

    CalamityGallopingInfernoEffect() {
        super(Outcome.Benefit);
        staticText = "choose a nonlegendary creature that saddled it this turn " +
                "and create a tapped and attacking token that's a copy of it. " +
                "Sacrifice that token at the beginning of the next end step. Repeat this process once";
    }

    private CalamityGallopingInfernoEffect(final CalamityGallopingInfernoEffect effect) {
        super(effect);
    }

    @Override
    public CalamityGallopingInfernoEffect copy() {
        return new CalamityGallopingInfernoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !game.getBattlefield().contains(filter, source, game, 1)) {
            return false;
        }
        for (int i = 0; i < 2; i++) {
            TargetPermanent target = new TargetPermanent(filter);
            target.withNotTarget(true);
            player.choose(outcome, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent == null) {
                continue;
            }
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                    null, null, false, 1, true, true
            );
            effect.apply(game, source);
            effect.sacrificeTokensCreatedAtNextEndStep(game, source);
        }
        return true;
    }
}
