package mage.cards.a;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.AddCreatureSubTypeAllMultiZoneEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreatureSpell;
import mage.filter.common.FilterOwnedCreatureCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArcaneAdaptation extends CardImpl {

    public static final FilterControlledCreatureSpell filterSpells = new FilterControlledCreatureSpell("creature spells you control");
    public static final FilterOwnedCreatureCard filterCards = new FilterOwnedCreatureCard("creature cards you own");

    public ArcaneAdaptation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // As Arcane Adaptation enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Neutral)));

        // Creatures you control are the chosen type in addition to their other types. The same is true for creature spells you control and creature cards you own that aren't on the battlefield.
        this.addAbility(new SimpleStaticAbility(new AddCreatureSubTypeAllMultiZoneEffect(
                StaticFilters.FILTER_CONTROLLED_CREATURES,
                filterSpells,
                filterCards
        )));
    }

    private ArcaneAdaptation(final ArcaneAdaptation card) {
        super(card);
    }

    @Override
    public ArcaneAdaptation copy() {
        return new ArcaneAdaptation(this);
    }
}