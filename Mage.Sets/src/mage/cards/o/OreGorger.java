

package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetNonBasicLandPermanent;

/**
 * @author Loki
 */
public final class OreGorger extends CardImpl {

    public OreGorger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
        Ability ability = new SpellCastControllerTriggeredAbility(new DestroyTargetEffect(), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, true);
        ability.addTarget(new TargetNonBasicLandPermanent());
        this.addAbility(ability);
    }

    private OreGorger(final OreGorger card) {
        super(card);
    }

    @Override
    public OreGorger copy() {
        return new OreGorger(this);
    }

}
