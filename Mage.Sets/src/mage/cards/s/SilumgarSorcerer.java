
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ExploitCreatureTriggeredAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.ExploitAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
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
public final class SilumgarSorcerer extends CardImpl {

    public SilumgarSorcerer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Exploit (When this creature enters the battlefield, you may sacrifice a creature.)
        this.addAbility(new ExploitAbility());

        // When Silumgar Sorcerer exploits a creature, counter target creature spell.
        Ability ability = new ExploitCreatureTriggeredAbility(new CounterTargetEffect(), false);
        ability.addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_CREATURE));
        this.addAbility(ability);
    }

    private SilumgarSorcerer(final SilumgarSorcerer card) {
        super(card);
    }

    @Override
    public SilumgarSorcerer copy() {
        return new SilumgarSorcerer(this);
    }
}
