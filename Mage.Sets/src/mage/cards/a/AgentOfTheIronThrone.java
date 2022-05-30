package mage.cards.a;

import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AgentOfTheIronThrone extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("an artifact or creature you control");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public AgentOfTheIronThrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have "Whenever an artifact or creature you control is put into a graveyard from the battlefield, each opponent loses 1 life."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                        new LoseLifeOpponentsEffect(1), false, filter, false
                ), Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        )));
    }

    private AgentOfTheIronThrone(final AgentOfTheIronThrone card) {
        super(card);
    }

    @Override
    public AgentOfTheIronThrone copy() {
        return new AgentOfTheIronThrone(this);
    }
}
