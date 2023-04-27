package mage.cards.s;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutCardIntoPlayWithHasteAndSacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SneakAttack extends CardImpl {

    public SneakAttack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // {R}: You may put a creature card from your hand onto the battlefield. That creature gains haste. Sacrifice the creature at the beginning of the next end step.
        this.addAbility(new SimpleActivatedAbility(
                new PutCardIntoPlayWithHasteAndSacrificeEffect(StaticFilters.FILTER_CARD_CREATURE), new ManaCostsImpl<>("{R}")
        ));
    }

    private SneakAttack(final SneakAttack card) {
        super(card);
    }

    @Override
    public SneakAttack copy() {
        return new SneakAttack(this);
    }
}

