package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.BlitzAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CaldaiaStrongarm extends CardImpl {

    public CaldaiaStrongarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Caldaia Strongarm enters the battlefield, put two +1/+1 counters on target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Blitz {3}{G}
        this.addAbility(new BlitzAbility("{3}{G}"));
    }

    private CaldaiaStrongarm(final CaldaiaStrongarm card) {
        super(card);
    }

    @Override
    public CaldaiaStrongarm copy() {
        return new CaldaiaStrongarm(this);
    }
}
