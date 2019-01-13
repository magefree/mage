package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OneOrMoreCountersAddedTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.AdaptAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BenthicBiomancer extends CardImpl {

    public BenthicBiomancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{U}: Adapt 1.
        this.addAbility(new AdaptAbility(1, "{1}{U}"));

        // Whenever one or more +1/+1 counters are put on Benthic Biomancer, draw a card, then discard a card.
        Ability ability = new OneOrMoreCountersAddedTriggeredAbility(new DrawCardSourceControllerEffect(1));
        ability.addEffect(new DiscardControllerEffect(1).setText(", then discard a card"));
        this.addAbility(ability);
    }

    private BenthicBiomancer(final BenthicBiomancer card) {
        super(card);
    }

    @Override
    public BenthicBiomancer copy() {
        return new BenthicBiomancer(this);
    }
}
