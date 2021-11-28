package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ExploitCreatureTriggeredAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.ExploitAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetStackObject;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OverchargedAmalgam extends CardImpl {

    public OverchargedAmalgam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Exploit
        this.addAbility(new ExploitAbility());

        // When Overcharged Amalgam exploits a creature, counter target spell, activated ability, or triggered ability.
        Ability ability = new ExploitCreatureTriggeredAbility(new CounterTargetEffect());
        ability.addTarget(new TargetStackObject());
        this.addAbility(ability);
    }

    private OverchargedAmalgam(final OverchargedAmalgam card) {
        super(card);
    }

    @Override
    public OverchargedAmalgam copy() {
        return new OverchargedAmalgam(this);
    }
}
