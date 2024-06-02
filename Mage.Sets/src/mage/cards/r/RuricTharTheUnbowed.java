
package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class RuricTharTheUnbowed extends CardImpl {

    public RuricTharTheUnbowed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Ruric Thar, the Unbowed attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // Whenever a player casts a noncreature spell, Ruric Thar deals 6 damage to that player.
        this.addAbility(new SpellCastAllTriggeredAbility(
                new DamageTargetEffect(6),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE,
                false, SetTargetPointer.PLAYER
        ));
    }

    private RuricTharTheUnbowed(final RuricTharTheUnbowed card) {
        super(card);
    }

    @Override
    public RuricTharTheUnbowed copy() {
        return new RuricTharTheUnbowed(this);
    }
}