package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.SnakeToken;
import mage.game.permanent.token.TokenImpl;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author LevelX
 */
public final class OrochiEggwatcher extends CardImpl {

    public OrochiEggwatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.flipCard = true;
        this.flipCardName = "Shidako, Broodmistress";

        // {2}{G}, {T}: Create a 1/1 green Snake creature token. If you control ten or more creatures, flip Orochi Eggwatcher.
        Ability ability;
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SnakeToken()), new ManaCostsImpl<>("{2}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new ConditionalOneShotEffect(new FlipSourceEffect(new ShidakoBroodmistress()),
                new PermanentsOnTheBattlefieldCondition(new FilterControlledCreaturePermanent(), ComparisonType.MORE_THAN, 9), "If you control ten or more creatures, flip {this}"));
        this.addAbility(ability.addHint(new ValueHint("Creatures you control", new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE))));
    }

    private OrochiEggwatcher(final OrochiEggwatcher card) {
        super(card);
    }

    @Override
    public OrochiEggwatcher copy() {
        return new OrochiEggwatcher(this);
    }
}

class ShidakoBroodmistress extends TokenImpl {

    ShidakoBroodmistress() {
        super("Shidako, Broodmistress", "");
        this.supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.SNAKE);
        subtype.add(SubType.SHAMAN);
        power = new MageInt(3);
        toughness = new MageInt(3);
        // {G}, Sacrifice a creature: Target creature gets +3/+3 until end of turn.
        Ability ability;
        ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostTargetEffect(3, 3, Duration.EndOfTurn),
                new ManaCostsImpl<>("{G}"));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }
    private ShidakoBroodmistress(final ShidakoBroodmistress token) {
        super(token);
    }

    public ShidakoBroodmistress copy() {
        return new ShidakoBroodmistress(this);
    }
}
