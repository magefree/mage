
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class EldraziObligator extends CardImpl {

    public EldraziObligator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Haste
        this.addAbility(HasteAbility.getInstance());

        DoIfCostPaid costPaidEffect = new DoIfCostPaid(new GainControlTargetEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{1}{C}"));
        Effect untapEffect = new UntapTargetEffect();
        untapEffect.setText("untap that creature,");
        Effect hasteEffect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        hasteEffect.setText("and it gains haste until end of turn");
        costPaidEffect.addEffect(untapEffect);
        costPaidEffect.addEffect(hasteEffect);

        // When you cast Eldrazi Obligator, you may pay {1}{C}. If you do, gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn.
        Ability ability = new CastSourceTriggeredAbility(costPaidEffect);

        Target target = new TargetCreaturePermanent(new FilterCreaturePermanent());
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private EldraziObligator(final EldraziObligator card) {
        super(card);
    }

    @Override
    public EldraziObligator copy() {
        return new EldraziObligator(this);
    }
}
