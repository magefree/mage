
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class YasovaDragonclaw extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls with power less than Yasova Dragonclaw's power");

    static {
        filter.add(new YasovaDragonclawPowerLessThanSourcePredicate());
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public YasovaDragonclaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // At the beginning of combat on your turn, you may pay {1}{(U/R)}{(U/R)}.  If you do, gain control of target creature an opponent controls with power less than Yasova Dragonclaw's power until end of turn, untap that creature, and it gains haste until end of turn.
        DoIfCostPaid effect = new DoIfCostPaid(new GainControlTargetEffect(Duration.EndOfTurn, true), new ManaCostsImpl<>("{1}{U/R}{U/R}"));
        Effect effect2 = new UntapTargetEffect();
        effect2.setText(", untap that creature");
        effect.addEffect(effect2);
        effect.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn, ", and it gains haste until end of turn"));
        Ability ability = new BeginningOfCombatTriggeredAbility(effect, TargetController.YOU, false);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private YasovaDragonclaw(final YasovaDragonclaw card) {
        super(card);
    }

    @Override
    public YasovaDragonclaw copy() {
        return new YasovaDragonclaw(this);
    }
}

class YasovaDragonclawPowerLessThanSourcePredicate implements ObjectSourcePlayerPredicate<Permanent> {

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent sourcePermanent = input.getSource().getSourcePermanentOrLKI(game);
        return sourcePermanent != null && input.getObject().getPower().getValue() < sourcePermanent.getPower().getValue();
    }

    @Override
    public String toString() {
        return "power less than Yasova Dragonclaw's power";
    }
}
