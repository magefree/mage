package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheCauldronOfEternity extends CardImpl {

    public TheCauldronOfEternity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{10}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);

        // This spell costs {2} less to cast for each creature card in your graveyard.
        DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE);
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SpellCostReductionForEachSourceEffect(2, xValue));
        ability.setRuleAtTheTop(true);
        ability.addHint(new ValueHint("Creature card in your graveyard", xValue));
        this.addAbility(ability);

        // Whenever a creature you control dies, put it on the bottom of its owner's library.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new PutOnLibraryTargetEffect(false, "put it on the bottom of its owner's library"),
                false, StaticFilters.FILTER_CONTROLLED_A_CREATURE, true
        ));

        // {2}{B}, {T}, Pay 2 life: Return target creature card from your graveyard to the battlefield. Activate this ability only any time you could cast a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(), new ManaCostsImpl<>("{2}{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new PayLifeCost(2));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private TheCauldronOfEternity(final TheCauldronOfEternity card) {
        super(card);
    }

    @Override
    public TheCauldronOfEternity copy() {
        return new TheCauldronOfEternity(this);
    }
}
