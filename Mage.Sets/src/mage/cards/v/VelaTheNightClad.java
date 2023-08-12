
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.ZoneChangeAllTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class VelaTheNightClad extends CardImpl {

    private static final String rule = "Whenever {this} or another creature you control leaves the battlefield, ";
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public VelaTheNightClad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Intimidate
        this.addAbility(IntimidateAbility.getInstance());
        // Other creatures you control have intimidate.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(IntimidateAbility.getInstance(),
                        Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURE, true)));
        // Whenever Vela the Night-Clad or another creature you control leaves the battlefield, each opponent loses 1 life.
        Ability ability = new ZoneChangeAllTriggeredAbility(
                Zone.BATTLEFIELD,
                Zone.BATTLEFIELD, null,
                new LoseLifeOpponentsEffect(1),
                filter, rule, false);
        this.addAbility(ability);
    }

    private VelaTheNightClad(final VelaTheNightClad card) {
        super(card);
    }

    @Override
    public VelaTheNightClad copy() {
        return new VelaTheNightClad(this);
    }
}
