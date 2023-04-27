package mage.cards.t;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutCardIntoPlayWithHasteAndSacrificeEffect;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ThroughTheBreach extends CardImpl {

    public ThroughTheBreach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");
        this.subtype.add(SubType.ARCANE);

        // You may put a creature card from your hand onto the battlefield. That creature gains haste. Sacrifice that creature at the beginning of the next end step.
        this.getSpellAbility().addEffect(new PutCardIntoPlayWithHasteAndSacrificeEffect(StaticFilters.FILTER_CARD_CREATURE));

        // Splice onto Arcane {2}{R}{R}
        this.addAbility(new SpliceAbility(SpliceAbility.ARCANE, new ManaCostsImpl<>("{2}{R}{R}")));
    }

    private ThroughTheBreach(final ThroughTheBreach card) {
        super(card);
    }

    @Override
    public ThroughTheBreach copy() {
        return new ThroughTheBreach(this);
    }
}
