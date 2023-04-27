package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllDamageToSourceByPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class ChampionLancer extends CardImpl {

    public ChampionLancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Prevent all damage that would be dealt to Champion Lancer by creatures.
        this.addAbility(new SimpleStaticAbility(new PreventAllDamageToSourceByPermanentsEffect(StaticFilters.FILTER_PERMANENT_CREATURES)));
    }

    private ChampionLancer(final ChampionLancer card) {
        super(card);
    }

    @Override
    public ChampionLancer copy() {
        return new ChampionLancer(this);
    }
}
