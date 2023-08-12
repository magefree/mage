package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class JaradGolgariLichLord extends CardImpl {

    private static final FilterControlledPermanent filterSwamp = new FilterControlledPermanent("a Swamp");
    private static final FilterControlledPermanent filterForest = new FilterControlledPermanent("a Forest");

    static {
        filterSwamp.add(SubType.SWAMP.getPredicate());
        filterForest.add(SubType.FOREST.getPredicate());
    }

    public JaradGolgariLichLord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.ELF);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Jarad, Golgari Lich Lord gets +1/+1 for each creature card in your graveyard.
        DynamicValue amount = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE);
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(amount, amount, Duration.WhileOnBattlefield));
        this.addAbility(ability);

        // {1}{B}{G}, Sacrifice another creature: Each opponent loses life equal to the sacrificed creature's power.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeOpponentsEffect(SacrificeCostCreaturesPower.instance), new ManaCostsImpl<>("{1}{B}{G}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, false)));
        this.addAbility(ability);

        // Sacrifice a Swamp and a Forest: Return Jarad from your graveyard to your hand.
        ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(),
                new SacrificeTargetCost(new TargetControlledPermanent(filterSwamp)));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filterForest)));
        this.addAbility(ability);

    }

    private JaradGolgariLichLord(final JaradGolgariLichLord card) {
        super(card);
    }

    @Override
    public JaradGolgariLichLord copy() {
        return new JaradGolgariLichLord(this);
    }
}
