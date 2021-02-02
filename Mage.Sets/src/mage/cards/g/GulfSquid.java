
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.TapAllTargetPlayerControlsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class GulfSquid extends CardImpl {

    public GulfSquid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.SQUID);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Gulf Squid enters the battlefield, tap all lands target player controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapAllTargetPlayerControlsEffect(StaticFilters.FILTER_LANDS));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private GulfSquid(final GulfSquid card) {
        super(card);
    }

    @Override
    public GulfSquid copy() {
        return new GulfSquid(this);
    }
}
