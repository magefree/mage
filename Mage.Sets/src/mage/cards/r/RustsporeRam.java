
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public final class RustsporeRam extends CardImpl {

    public RustsporeRam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.SHEEP);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_EQUIPMENT));
        this.addAbility(ability);
    }

    private RustsporeRam(final RustsporeRam card) {
        super(card);
    }

    @Override
    public RustsporeRam copy() {
        return new RustsporeRam(this);
    }
}
