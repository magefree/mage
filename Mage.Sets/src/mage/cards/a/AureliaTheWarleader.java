
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksFirstTimeTriggeredAbility;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class AureliaTheWarleader extends CardImpl {

    public AureliaTheWarleader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying, vigilance, haste
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());

        // Whenever Aurelia, the Warleader attacks for the first time each turn, untap all creatures you control. After this phase, there is an additional combat phase.
        Ability ability = new AttacksFirstTimeTriggeredAbility(
                new UntapAllControllerEffect(
                        StaticFilters.FILTER_CONTROLLED_CREATURES,
                        "untap all creatures you control"
                ),
                false);
        ability.addEffect(new AdditionalCombatPhaseEffect());
        this.addAbility(ability);

    }

    private AureliaTheWarleader(final AureliaTheWarleader card) {
        super(card);
    }

    @Override
    public AureliaTheWarleader copy() {
        return new AureliaTheWarleader(this);
    }
}
