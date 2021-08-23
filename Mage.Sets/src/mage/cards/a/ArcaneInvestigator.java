package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArcaneInvestigator extends CardImpl {

    public ArcaneInvestigator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Search the Room — {5}{U}: Roll a d20.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect();
        this.addAbility(new SimpleActivatedAbility(
                effect, new ManaCostsImpl<>("{5}{U}")
        ).withFlavorWord("Search the Room"));

        // 1-9 | Draw a card.
        effect.addTableEntry(1, 9, new DrawCardSourceControllerEffect(1));

        // 10-20 | Look at the top three cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.
        effect.addTableEntry(10, 20, new LookLibraryAndPickControllerEffect(
                StaticValue.get(3), false, StaticValue.get(1),
                StaticFilters.FILTER_CARD, Zone.LIBRARY, false, false
        ).setText("look at the top three cards of your library. Put one of them " +
                "into your hand and the rest on the bottom of your library in any order"));
    }

    private ArcaneInvestigator(final ArcaneInvestigator card) {
        super(card);
    }

    @Override
    public ArcaneInvestigator copy() {
        return new ArcaneInvestigator(this);
    }
}
