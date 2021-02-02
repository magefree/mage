
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class DualcasterMage extends CardImpl {

    public DualcasterMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        
        // When Dualcaster Mage enters the battlefield, copy target instant or sorcery spell. You may choose new targets for the copy.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CopyTargetSpellEffect(), false);
        ability.addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));
        this.addAbility(ability);

    }

    private DualcasterMage(final DualcasterMage card) {
        super(card);
    }

    @Override
    public DualcasterMage copy() {
        return new DualcasterMage(this);
    }
}
