package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.ConstructToken;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JanJansenChaosCrafter extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledArtifactPermanent("artifact creature");
    private static final FilterControlledPermanent filter2
            = new FilterControlledArtifactPermanent("noncreature artifact");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter2.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public JanJansenChaosCrafter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GNOME);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {T}, Sacrifice an artifact creature: Create two Treasure tokens.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new TreasureToken(), 2), new TapSourceCost()
        );
        ability.addCost(new SacrificeTargetCost(filter));
        this.addAbility(ability);

        // {T}, Sacrifice a noncreature artifact: Create two 1/1 colorless Construct artifact creature tokens.
        ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new ConstructToken(), 2), new TapSourceCost()
        );
        ability.addCost(new SacrificeTargetCost(filter2));
        this.addAbility(ability);
    }

    private JanJansenChaosCrafter(final JanJansenChaosCrafter card) {
        super(card);
    }

    @Override
    public JanJansenChaosCrafter copy() {
        return new JanJansenChaosCrafter(this);
    }
}
