package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class PasswallAdept extends CardImpl {

    public PasswallAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {2}{U}: Target creature can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new CantBeBlockedTargetEffect(),
                new ManaCostsImpl<>("{2}{U}")
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private PasswallAdept(final PasswallAdept card) {
        super(card);
    }

    @Override
    public PasswallAdept copy() {
        return new PasswallAdept(this);
    }
}
