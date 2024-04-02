package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWhileSaddledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.SaddleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.SaddledSourceThisTurnPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTargets;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FortuneLoyalSteed extends CardImpl {

    public FortuneLoyalSteed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Fortune, Loyal Steed enters the battlefield, scry 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(2, false)));

        // Whenever Fortune attacks while saddled, at end of combat, exile it and up to one creature that saddled it this turn, then return those cards to the battlefield under their owner's control.
        this.addAbility(new AttacksWhileSaddledTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheEndOfCombatDelayedTriggeredAbility(new FortuneLoyalSteedEffect())
        )));

        // Saddle 1
        this.addAbility(new SaddleAbility(1));
    }

    private FortuneLoyalSteed(final FortuneLoyalSteed card) {
        super(card);
    }

    @Override
    public FortuneLoyalSteed copy() {
        return new FortuneLoyalSteed(this);
    }
}

class FortuneLoyalSteedEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature that saddled it this turn");

    static {
        filter.add(SaddledSourceThisTurnPredicate.instance);
    }

    FortuneLoyalSteedEffect() {
        super(Outcome.Benefit);
        staticText = "exile it and up to one creature that saddled it this turn, " +
                "then return those cards to the battlefield under their owner's control";
    }

    private FortuneLoyalSteedEffect(final FortuneLoyalSteedEffect effect) {
        super(effect);
    }

    @Override
    public FortuneLoyalSteedEffect copy() {
        return new FortuneLoyalSteedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        List<Permanent> permanents = new ArrayList<>();
        permanents.add(source.getSourcePermanentIfItStillExists(game));
        TargetPermanent target = new TargetPermanent(0, 1, filter, true);
        player.choose(outcome, target, source, game);
        permanents.add(game.getPermanent(target.getFirstTarget()));
        permanents.removeIf(Objects::isNull);
        return permanents.isEmpty()
                ? false
                : new ExileThenReturnTargetEffect(false, false)
                .setTargetPointer(new FixedTargets(permanents, game))
                .apply(game, source);
    }
}
