
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class WildfireCerberus extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public WildfireCerberus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.DOG);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // {5}{R}{R}: Monstrosity 1.
        this.addAbility(new MonstrosityAbility("{5}{R}{R}", 1));

        // When Wildfire Cerberus becomes monstrous, it deals 2 damage to each opponent and each creature your opponents control.
        Effect effect = new DamagePlayersEffect(2, TargetController.OPPONENT);
        effect.setText("it deals 2 damage to each opponent");
        Ability ability = new BecomesMonstrousSourceTriggeredAbility(effect);
        effect = new DamageAllEffect(2, filter);
        effect.setText("and each creature your opponents control");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private WildfireCerberus(final WildfireCerberus card) {
        super(card);
    }

    @Override
    public WildfireCerberus copy() {
        return new WildfireCerberus(this);
    }
}
