package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ExploitCreatureTriggeredAbility;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.ExploitAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FellStinger extends CardImpl {

    public FellStinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SCORPION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Exploit
        this.addAbility(new ExploitAbility());

        // When Fell Stinger exploits a creature, target player draws two cards and loses 2 life.
        Ability ability = new ExploitCreatureTriggeredAbility(new DrawCardTargetEffect(2));
        ability.addEffect(new LoseLifeTargetEffect(2));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private FellStinger(final FellStinger card) {
        super(card);
    }

    @Override
    public FellStinger copy() {
        return new FellStinger(this);
    }
}
