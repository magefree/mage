package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.effects.common.combat.BlocksIfAbleTargetEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class Rathtar extends CardImpl {

    public Rathtar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");
        
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {5}{G}{G}: Monstrosity 2.
        this.addAbility(new MonstrosityAbility("{5}{G}{G}", 2));

        // When Rathtar becomes monstrous, any number of target creatures must block it this turn if able.
        Ability ability = new BecomesMonstrousSourceTriggeredAbility(
                new BlocksIfAbleTargetEffect(Duration.EndOfTurn).setText("any number of target creatures must block it this turn if able"));
        ability.addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));
        this.addAbility(ability);
    }

    private Rathtar(final Rathtar card) {
        super(card);
    }

    @Override
    public Rathtar copy() {
        return new Rathtar(this);
    }
}
