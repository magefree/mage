package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesToughness;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author fireshoes
 */
public final class AyliEternalPilgrim extends CardImpl {

    public AyliEternalPilgrim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KOR, SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // {1}, Sacrifice another creature: You gain life equal to the sacrificed creature's toughness.
        Effect effect = new GainLifeEffect(SacrificeCostCreaturesToughness.instance);
        effect.setText("You gain life equal to the sacrificed creature's toughness");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)));
        this.addAbility(ability);

        // {1}{W}{B}, Sacrifice another creature: Exile target nonland permanent. Activate only if you have at least 10 life more than your starting life total.
        ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new ExileTargetEffect(),
                new ManaCostsImpl<>("{1}{W}{B}"),
                new AyliEternalPilgrimCondition()
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)));
        ability.addTarget(new TargetNonlandPermanent().withChooseHint("to exile"));
        this.addAbility(ability);
    }

    private AyliEternalPilgrim(final AyliEternalPilgrim card) {
        super(card);
    }

    @Override
    public AyliEternalPilgrim copy() {
        return new AyliEternalPilgrim(this);
    }
}

class AyliEternalPilgrimCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if(player != null) {
            return player.getLife() >= game.getStartingLife() + 10;
        }
        return false;
    }

    @Override
    public String toString() {
        return "you have at least 10 life more than your starting life total";
    }
}
