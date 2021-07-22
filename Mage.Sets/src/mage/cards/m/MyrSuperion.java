
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author BetaSteward
 */
public final class MyrSuperion extends CardImpl {


    public MyrSuperion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.MYR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Spend only mana produced by creatures to cast Myr Superion.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("Spend only mana produced by creatures to cast this spell")));
        this.getSpellAbility().getManaCostsToPay().setSourceFilter(StaticFilters.FILTER_PERMANENT_CREATURES);
        this.getSpellAbility().getManaCosts().setSourceFilter(StaticFilters.FILTER_PERMANENT_CREATURES);
    }

    private MyrSuperion(final MyrSuperion card) {
        super(card);
    }

    @Override
    public MyrSuperion copy() {
        return new MyrSuperion(this);
    }
}
