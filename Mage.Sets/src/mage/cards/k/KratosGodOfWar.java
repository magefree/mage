package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackedThisTurnPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KratosGodOfWar extends CardImpl {

    public KratosGodOfWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // All creatures have haste.
        this.addAbility(
                new SimpleStaticAbility(
                        new GainAbilityAllEffect(
                            HasteAbility.getInstance(), Duration.WhileOnBattlefield,
                            StaticFilters.FILTER_PERMANENT_CREATURES
                            ).setText("all creatures have haste")
                ));

        // At the beginning of each player's end step, Kratos deals damage to that player equal to the number of creatures that player controls that didn't attack this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.EACH_PLAYER, new KratosGodOfWarEffect(), false
        ));
    }

    private KratosGodOfWar(final KratosGodOfWar card) {
        super(card);
    }

    @Override
    public KratosGodOfWar copy() {
        return new KratosGodOfWar(this);
    }
}

class KratosGodOfWarEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(Predicates.not(AttackedThisTurnPredicate.instance));
    }

    KratosGodOfWarEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals damage to that player equal to the number of creatures " +
                "that player controls that didn't attack this turn.";
    }

    private KratosGodOfWarEffect(final KratosGodOfWarEffect effect) {
        super(effect);
    }

    @Override
    public KratosGodOfWarEffect copy() {
        return new KratosGodOfWarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player == null) {
            return false;
        }
        int count = game.getBattlefield().count(filter, game.getActivePlayerId(), source, game);
        return count > 0 && player.damage(count, source, game) > 0;
    }
}
