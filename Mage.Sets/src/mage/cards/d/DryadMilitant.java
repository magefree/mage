
package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.replacement.GraveyardFromAnywhereExileReplacementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class DryadMilitant extends CardImpl {

    public DryadMilitant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G/W}");
        this.subtype.add(SubType.DRYAD);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // If an instant or sorcery card would be put into a graveyard from anywhere, exile it instead.
        this.addAbility(new SimpleStaticAbility(new GraveyardFromAnywhereExileReplacementEffect(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, false)));
    }

    private DryadMilitant(final DryadMilitant card) {
        super(card);
    }

    @Override
    public DryadMilitant copy() {
        return new DryadMilitant(this);
    }
}
