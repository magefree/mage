package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JuvenileMistDragon extends CardImpl {

    public JuvenileMistDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Confounding Clouds — When Juvenile Mist Dragon enters the battlefield, for each opponent, tap up to one target creature that player controls. Each of those creatures doesn't untap during its controller's next untap step.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new TapTargetEffect()
                        .setTargetPointer(new EachTargetPointer())
                        .setText("for each opponent, tap up to one target creature that player controls")
        );
        ability.addEffect(
                new DontUntapInControllersNextUntapStepTargetEffect("")
                        .setTargetPointer(new EachTargetPointer())
                        .setText("Each of those creatures doesn't untap during its controller's next untap step")
        );
        ability.setTargetAdjuster(JuvenileMistDragonAdjuster.instance);
        this.addAbility(ability.withFlavorWord("Confounding Clouds"));
    }

    private JuvenileMistDragon(final JuvenileMistDragon card) {
        super(card);
    }

    @Override
    public JuvenileMistDragon copy() {
        return new JuvenileMistDragon(this);
    }
}

enum JuvenileMistDragonAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        for (UUID opponentId : game.getOpponents(ability.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            FilterPermanent filter = new FilterCreaturePermanent("creature controlled by " + opponent.getLogName());
            filter.add(new ControllerIdPredicate(opponentId));
            TargetPermanent target = new TargetPermanent(0, 1, filter, false);
            ability.addTarget(target);
        }
    }
}
