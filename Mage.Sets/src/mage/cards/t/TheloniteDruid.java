
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class TheloniteDruid extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("Forests you control");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public TheloniteDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{G}, {tap}, Sacrifice a creature: Forests you control become 2/3 creatures until end of turn. They're still lands.
        ContinuousEffect effect = new BecomesCreatureAllEffect(
                new CreatureToken(2, 3),
                "Forests", filter, Duration.EndOfTurn, false);
        effect.getDependencyTypes().add(DependencyType.BecomeForest);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                effect,
                new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        this.addAbility(ability);
    }

    private TheloniteDruid(final TheloniteDruid card) {
        super(card);
    }

    @Override
    public TheloniteDruid copy() {
        return new TheloniteDruid(this);
    }
}

class TheloniteDruidLandToken extends TokenImpl {

    public TheloniteDruidLandToken() {
        super("", "2/3 creatures");
        cardType.add(CardType.CREATURE);
        power = new MageInt(2);
        toughness = new MageInt(3);
    }
    public TheloniteDruidLandToken(final TheloniteDruidLandToken token) {
        super(token);
    }

    public TheloniteDruidLandToken copy() {
        return new TheloniteDruidLandToken(this);
    }
}
