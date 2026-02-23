package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoubleCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.SaprolingToken;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FerraforYoungYew extends CardImpl {

    public FerraforYoungYew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        // When Ferrafor enters, create a number of 1/1 green Saproling creature tokens equal to the number of counters among creatures target player controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(
                new SaprolingToken(), FerraforYoungYewValue.instance
        ).setText("create a number of 1/1 green Saproling creature tokens equal " +
                "to the number of counters among creatures target player controls"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // {T}: Double the number of each kind of counter on target creature.
        ability = new SimpleActivatedAbility(new DoubleCountersTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private FerraforYoungYew(final FerraforYoungYew card) {
        super(card);
    }

    @Override
    public FerraforYoungYew copy() {
        return new FerraforYoungYew(this);
    }
}

enum FerraforYoungYewValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(effect.getTargetPointer().getFirst(game, sourceAbility));
        return player != null
                ? game
                .getBattlefield()
                .getActivePermanents(StaticFilters.FILTER_CONTROLLED_CREATURE, player.getId(), sourceAbility, game)
                .stream()
                .mapToInt(permanent -> permanent.getCounters(game).getTotalCount())
                .sum()
                : 0;
    }

    @Override
    public FerraforYoungYewValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}
