package mage.cards.a;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArcaneInfusion extends CardImpl {

    public ArcaneInfusion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{R}");

        // Look at the top four cards of your library. You may reveal an instant or sorcery card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                StaticValue.get(4), false, StaticValue.get(1),
                StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, Zone.LIBRARY,
                false, true, false, Zone.HAND,
                true, false, false
        ).setBackInRandomOrder(true).setText("Look at the top four cards of your library. " +
                "You may reveal an instant or sorcery card from among them and put it into your hand. " +
                "Put the rest on the bottom of your library in a random order."));

        // Flashback {3}{U}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{3}{U}{R}")));
    }

    private ArcaneInfusion(final ArcaneInfusion card) {
        super(card);
    }

    @Override
    public ArcaneInfusion copy() {
        return new ArcaneInfusion(this);
    }
}
