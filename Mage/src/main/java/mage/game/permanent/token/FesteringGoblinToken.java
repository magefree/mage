

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author spjspj
 */
public final class FesteringGoblinToken extends TokenImpl {

    public FesteringGoblinToken() {
        super("Festering Goblin", "1/1 black Zombie Goblin creature token named Festering Goblin. It has \"When Festering Goblin dies, target creature gets -1/-1 until end of turn.\"");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ZOMBIE);
        subtype.add(SubType.GOBLIN);
        power = new MageInt(1);
        toughness = new MageInt(1);

        Ability ability = new DiesSourceTriggeredAbility(new BoostTargetEffect(-1, -1, Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public FesteringGoblinToken(final FesteringGoblinToken token) {
        super(token);
    }

    public FesteringGoblinToken copy() {
        return new FesteringGoblinToken(this);
    }
}
