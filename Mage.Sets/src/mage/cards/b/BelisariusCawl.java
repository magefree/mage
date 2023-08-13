package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.common.TapVariableTargetCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.WhiteAstartesWarriorToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author @stwalsh4118
 */
public final class BelisariusCawl extends CardImpl {

    private static final FilterControlledArtifactPermanent filter = new FilterControlledArtifactPermanent("untapped artifacts you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public BelisariusCawl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W}{U}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Ultima Founding -- {T}, Tap two untapped artifacts you control: Create a 2/2 white Astartes Warrior creature token with vigilance.
        Effect effect = new CreateTokenEffect(new WhiteAstartesWarriorToken());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(2, 2, filter, true)));
        this.addAbility(ability.withFlavorWord("Ultima Founding"));

        // Master of Machines -- {T}, Tap X untapped creatures you control: Look at the top X cards of your library. You may reveal an artifact card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LookLibraryAndPickControllerEffect(GetXValue.instance, 1, StaticFilters.FILTER_CARD_ARTIFACT, PutCards.HAND, PutCards.BOTTOM_RANDOM, true), new TapSourceCost());
        ability.addCost(new TapVariableTargetCost(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES));
        this.addAbility(ability.withFlavorWord("Master of Machines"));
    }

    private BelisariusCawl(final BelisariusCawl card) {
        super(card);
    }

    @Override
    public BelisariusCawl copy() {
        return new BelisariusCawl(this);
    }
}
