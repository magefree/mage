
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.MeditateAbility;
import mage.abilities.keyword.SpaceflightAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class JediStarfighter extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Jedi creatures you control");

    static {
        filter.add(SubType.JEDI.getPredicate());
               filter.add(TargetController.YOU.getControllerPredicate());
    }

    public JediStarfighter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.JEDI);
        this.subtype.add(SubType.STARSHIP);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Spaceflight
        this.addAbility(SpaceflightAbility.getInstance());

        // When Jedi Starfighter enters the battlefield, up to two Jedi creatures you control gain spaceflight until end of turn.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new GainAbilityTargetEffect(SpaceflightAbility.getInstance(), Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent(0, 2, filter, true));
        this.addAbility(ability);

        // Meditate {1}{W}
        this.addAbility(new MeditateAbility(new ManaCostsImpl<>("{1}{W}")));
    }

    private JediStarfighter(final JediStarfighter card) {
        super(card);
    }

    @Override
    public JediStarfighter copy() {
        return new JediStarfighter(this);
    }
}
