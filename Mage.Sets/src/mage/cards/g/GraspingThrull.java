package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GraspingThrull extends CardImpl {

    public GraspingThrull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}");

        this.subtype.add(SubType.THRULL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Grasping Thrull enters the battlefield, it deals 2 damage to each opponent and you gain 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamagePlayersEffect(
                2, TargetController.OPPONENT, "it"
        ));
        ability.addEffect(new GainLifeEffect(2).setText("and you gain 2 life"));
        this.addAbility(ability);
    }

    private GraspingThrull(final GraspingThrull card) {
        super(card);
    }

    @Override
    public GraspingThrull copy() {
        return new GraspingThrull(this);
    }
}
