
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class GempalmPolluter extends CardImpl {

    static final private FilterPermanent filter = new FilterPermanent("Zombie");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public GempalmPolluter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Cycling {B}{B}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{B}{B}")));

        // When you cycle Gempalm Polluter, you may have target player lose life equal to the number of Zombies on the battlefield.
        Effect effect = new LoseLifeTargetEffect(new PermanentsOnBattlefieldCount(filter));
        effect.setText("you may have target player lose life equal to the number of Zombies on the battlefield");
        Ability ability = new CycleTriggeredAbility(effect, true);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private GempalmPolluter(final GempalmPolluter card) {
        super(card);
    }

    @Override
    public GempalmPolluter copy() {
        return new GempalmPolluter(this);
    }
}
