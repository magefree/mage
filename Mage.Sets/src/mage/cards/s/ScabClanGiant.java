
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ScabClanGiant extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public ScabClanGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{G}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Scab-Clan Giant enters the battlefield, it fights target creature an opponent controls chosen at random.
        Ability ability = new EntersBattlefieldTriggeredAbility(new FightTargetSourceEffect()
                .setText("it fights target creature an opponent controls chosen at random"));
        Target target = new TargetCreaturePermanent(filter);
        target.setRandom(true);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private ScabClanGiant(final ScabClanGiant card) {
        super(card);
    }

    @Override
    public ScabClanGiant copy() {
        return new ScabClanGiant(this);
    }
}
