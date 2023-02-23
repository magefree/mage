package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.common.TapVariableTargetCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.game.permanent.token.WhiteAstartesWarriorToken;

/**
 *
 * @author @stwalsh4118
 */
public final class BelisariusCawl extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped artifacts you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(CardType.ARTIFACT.getPredicate());
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
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
        effect.setText("Create a 2/2 white Astartes Warrior creature token with vigilance."), new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(2, 2, filter, true)));
        ability.withFlavorWord("Ultima Founding");
        this.addAbility(ability);

        // Master of Machines -- {T}, Tap X untapped creatures you control: Look at the top X cards of your library. You may reveal an artifact card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LookLibraryAndPickControllerEffect(GetXValue.instance, 1, StaticFilters.FILTER_CARD_ARTIFACT, PutCards.HAND, PutCards.BOTTOM_RANDOM, true), new TapSourceCost());
        ability.addCost(new TapVariableTargetCost(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES));
        ability.withFlavorWord("Master of Machines");
        this.addAbility(ability);
    }

    private BelisariusCawl(final BelisariusCawl card) {
        super(card);
    }

    @Override
    public BelisariusCawl copy() {
        return new BelisariusCawl(this);
    }
}
