
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksAloneSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;

/**
 *
 * @author LoneFox
 */
public final class MaChaoWesternWarrior extends CardImpl {

    public MaChaoWesternWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Horsemanship
        this.addAbility(HorsemanshipAbility.getInstance());
        // Whenever Ma Chao, Western Warrior attacks alone, it can't be blocked this combat.
        Effect effect = new CantBeBlockedSourceEffect(Duration.EndOfCombat);
        effect.setText("it can't be blocked this combat");
        this.addAbility(new AttacksAloneSourceTriggeredAbility(effect));
    }

    private MaChaoWesternWarrior(final MaChaoWesternWarrior card) {
        super(card);
    }

    @Override
    public MaChaoWesternWarrior copy() {
        return new MaChaoWesternWarrior(this);
    }
}
