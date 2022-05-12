
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class AtarkaEfreet extends CardImpl {

    public AtarkaEfreet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.EFREET);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(1);

        // Megamorph {2}{R}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{2}{R}"), true));

        // When Atarka Efreet is turned face up, it deals 1 damage to any target.
        Effect effect = new DamageTargetEffect(1, "it");
        effect.setText("it deals 1 damage to any target");
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(effect, false, false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

    }

    private AtarkaEfreet(final AtarkaEfreet card) {
        super(card);
    }

    @Override
    public AtarkaEfreet copy() {
        return new AtarkaEfreet(this);
    }
}
