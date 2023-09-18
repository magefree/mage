

package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Loki
 */
public final class HorizonSeed extends CardImpl {

    public HorizonSeed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        Ability ability = new SpellCastControllerTriggeredAbility(new RegenerateTargetEffect(), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private HorizonSeed(final HorizonSeed card) {
        super(card);
    }

    @Override
    public HorizonSeed copy() {
        return new HorizonSeed(this);
    }

}
