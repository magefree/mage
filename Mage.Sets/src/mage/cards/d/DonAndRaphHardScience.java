package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.NextSpellCastHasAbilityEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DonAndRaphHardScience extends CardImpl {

    public DonAndRaphHardScience(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U/R}{U/R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Don & Raph attack, the next noncreature spell you cast this turn has affinity for artifacts.
        this.addAbility(new AttacksTriggeredAbility(new NextSpellCastHasAbilityEffect(
                new AffinityForArtifactsAbility(), StaticFilters.FILTER_CARD_NON_CREATURE
        )).setTriggerPhrase("Whenever {this} attack, "));
    }

    private DonAndRaphHardScience(final DonAndRaphHardScience card) {
        super(card);
    }

    @Override
    public DonAndRaphHardScience copy() {
        return new DonAndRaphHardScience(this);
    }
}
