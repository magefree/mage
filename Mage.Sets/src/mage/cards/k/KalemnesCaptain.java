
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.effects.common.ExileAllEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class KalemnesCaptain extends CardImpl {

    public KalemnesCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        
        // {5}{W}{W}: Monstrosity 3.
        this.addAbility(new MonstrosityAbility("{5}{W}{W}", 3));
        
        // When Kalemne's Captain becomes monstrous, exile all artifacts and enchantments.
        this.addAbility(new BecomesMonstrousSourceTriggeredAbility(new ExileAllEffect(StaticFilters.FILTER_PERMANENT_ARTIFACTS_AND_ENCHANTMENTS)));
    }

    private KalemnesCaptain(final KalemnesCaptain card) {
        super(card);
    }

    @Override
    public KalemnesCaptain copy() {
        return new KalemnesCaptain(this);
    }
}
