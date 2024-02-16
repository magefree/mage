package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.keyword.CraftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VisageOfDread extends CardImpl {

    public VisageOfDread(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}");
        this.secondSideCardClazz = mage.cards.d.DreadOsseosaur.class;

        // When Visage of Dread enters the battlefield, target opponent reveals their hand. You choose an artifact or creature card from it. That player discards that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DiscardCardYouChooseTargetEffect(StaticFilters.FILTER_CARD_ARTIFACT_OR_CREATURE)
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Craft with two creatures {5}{B}
        this.addAbility(new CraftAbility(
                "{5}{B}", "two creatures", "other creatures you control and/or " +
                "creature cards in your graveyard", 2, 2, CardType.CREATURE.getPredicate()
        ));
    }

    private VisageOfDread(final VisageOfDread card) {
        super(card);
    }

    @Override
    public VisageOfDread copy() {
        return new VisageOfDread(this);
    }
}
