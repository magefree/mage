package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlloyAnimist extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledArtifactPermanent("noncreature artifact you control");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public AlloyAnimist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{G}: Until end of turn, target noncreature artifact you control becomes a 4/4 artifact creature.
        Ability ability = new SimpleActivatedAbility(new BecomesCreatureTargetEffect(
                new CreatureToken(4, 4, "4/4 artifact creature")
                        .withType(CardType.ARTIFACT),
                false, false, Duration.EndOfTurn
        ).withDurationRuleAtStart(true), new ManaCostsImpl<>("{2}{G}"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private AlloyAnimist(final AlloyAnimist card) {
        super(card);
    }

    @Override
    public AlloyAnimist copy() {
        return new AlloyAnimist(this);
    }
}
