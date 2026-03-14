package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.ClueArtifactToken;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheMechanistAerialArtisan extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent("artifact token you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public TheMechanistAerialArtisan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you cast a noncreature spell, create a Clue token.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new ClueArtifactToken()),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));

        // {T}: Until end of turn, target artifact token you control becomes a 3/1 Construct artifact creature with flying.
        Ability ability = new SimpleActivatedAbility(
                new BecomesCreatureTargetEffect(
                        new CreatureToken(
                                3, 1, "3/1 Construct artifact creature with flying", SubType.CONSTRUCT
                        ).withAbility(FlyingAbility.getInstance()), false, false, Duration.EndOfTurn
                ).withDurationRuleAtStart(true), new TapSourceCost()
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private TheMechanistAerialArtisan(final TheMechanistAerialArtisan card) {
        super(card);
    }

    @Override
    public TheMechanistAerialArtisan copy() {
        return new TheMechanistAerialArtisan(this);
    }
}
