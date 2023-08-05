package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsTargetOpponentControlsCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CavernHoardDragon extends CardImpl {

    public CavernHoardDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // This spell costs {X} less to cast, where X is the greatest number of artifacts an opponent controls.
        this.addAbility(new SimpleStaticAbility(
                        Zone.ALL,
                        new SpellCostReductionSourceEffect(CavernHoardDragonCount.instance)
                ).addHint(CavernHoardDragonCount.getHint())
        );

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Cavern-Hoard Dragon deals combat damage to a player, you create a Treasure token for each artifact that player controls.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new CreateTokenEffect(
                        new TreasureToken(),
                        new PermanentsTargetOpponentControlsCount(StaticFilters.FILTER_PERMANENT_ARTIFACT)
                ).setText("you create a Treasure token for each artifact that player controls"),
                false, true
        ));
    }

    private CavernHoardDragon(final CavernHoardDragon card) {
        super(card);
    }

    @Override
    public CavernHoardDragon copy() {
        return new CavernHoardDragon(this);
    }
}

enum CavernHoardDragonCount implements DynamicValue {
    instance;

    private static final Hint hint = new ValueHint(
            "Greatest number of artifacts an opponent controls", instance
    );

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return getValue(sourceAbility.getControllerId(), sourceAbility, game);
    }

    public static int getValue(UUID playerId, Ability source, Game game) {
        int max = 0;
        for (UUID opponentId : game.getOpponents(playerId)) {
            Player player = game.getPlayer(opponentId);
            if (player == null) {
                continue;
            }

            FilterArtifactPermanent filter = new FilterArtifactPermanent();
            filter.add(new ControllerIdPredicate(opponentId));

            int count = game.getBattlefield().count(filter, playerId, source, game);
            max = Math.max(count, max);
        }

        return max;
    }

    @Override
    public CavernHoardDragonCount copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "greatest number of artifacts an opponent controls";
    }

    public static Hint getHint() {
        return hint;
    }
}
