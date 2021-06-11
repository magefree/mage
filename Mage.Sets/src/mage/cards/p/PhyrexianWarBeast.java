
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
/**
 *
 * @author fireshoes
 */
public final class PhyrexianWarBeast extends CardImpl {

    public PhyrexianWarBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Phyrexian War Beast leaves the battlefield, sacrifice a land and Phyrexian War Beast deals 1 damage to you.
        Ability ability = new LeavesBattlefieldTriggeredAbility(new SacrificeControllerEffect(StaticFilters.FILTER_LAND, 1, ""), false);
        Effect effect = new DamageControllerEffect(1);
        effect.setText("and {this} deals 1 damage to you");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private PhyrexianWarBeast(final PhyrexianWarBeast card) {
        super(card);
    }

    @Override
    public PhyrexianWarBeast copy() {
        return new PhyrexianWarBeast(this);
    }
}
