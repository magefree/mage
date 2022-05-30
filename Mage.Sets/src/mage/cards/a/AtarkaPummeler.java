
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class AtarkaPummeler extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public AtarkaPummeler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // <i>Formidable</i> &mdash; {3}{R}{R}: Creatures you control gain menace until end of turn. Activate this ability only if creature you control have total power 8 or greater.  (They can't be blocked except by two or more creatures.)
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD,
                new GainAbilityAllEffect(new MenaceAbility(), Duration.EndOfTurn, filter),
                new ManaCostsImpl<>("{3}{R}{R}"),
                FormidableCondition.instance);
        ability.setAbilityWord(AbilityWord.FORMIDABLE);
        this.addAbility(ability);

    }

    private AtarkaPummeler(final AtarkaPummeler card) {
        super(card);
    }

    @Override
    public AtarkaPummeler copy() {
        return new AtarkaPummeler(this);
    }
}
