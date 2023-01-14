package mage.cards.a;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutCardIntoPlayWithHasteAndSacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArmsRace extends CardImpl {

    public ArmsRace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // {3}{R}: You may put an artifact card from your hand onto the battlefield. It gains haste. Sacrifice it at the beginning of the next end step.
        this.addAbility(new SimpleActivatedAbility(
                new PutCardIntoPlayWithHasteAndSacrificeEffect(StaticFilters.FILTER_CARD_ARTIFACT), new ManaCostsImpl<>("{3}{R}")
        ));
    }

    private ArmsRace(final ArmsRace card) {
        super(card);
    }

    @Override
    public ArmsRace copy() {
        return new ArmsRace(this);
    }
}
