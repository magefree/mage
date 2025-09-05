package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class LivingBrainMechanicalMarvel extends CardImpl {

    private static final FilterControlledArtifactPermanent filter = new FilterControlledArtifactPermanent("non-Equipment artifact you control");

    static {
        filter.add(Predicates.not(SubType.EQUIPMENT.getPredicate()));
    }

    public LivingBrainMechanicalMarvel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, target non-Equipment artifact you control becomes an artifact creature with base power and toughness 3/3 until end of turn. Untap it.
        CreatureToken token = new CreatureToken(3, 3, "artifact creature with base power and toughness 3/3")
                .withType(CardType.ARTIFACT);
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new BecomesCreatureTargetEffect(token, false, false, Duration.EndOfTurn, false, true, false)
        );
        ability.addEffect(new UntapTargetEffect("untap it"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private LivingBrainMechanicalMarvel(final LivingBrainMechanicalMarvel card) {
        super(card);
    }

    @Override
    public LivingBrainMechanicalMarvel copy() {
        return new LivingBrainMechanicalMarvel(this);
    }
}
