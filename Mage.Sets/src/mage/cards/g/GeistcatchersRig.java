
package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class GeistcatchersRig extends CardImpl {

    public GeistcatchersRig(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Geistcatcher's Rig enters the battlefield, you may have it deal 4 damage to target creature with flying.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(4), true);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
        this.addAbility(ability);
    }

    private GeistcatchersRig(final GeistcatchersRig card) {
        super(card);
    }

    @Override
    public GeistcatchersRig copy() {
        return new GeistcatchersRig(this);
    }
}
