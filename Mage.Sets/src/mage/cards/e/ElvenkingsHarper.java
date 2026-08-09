package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ElvenkingsHarper extends CardImpl {

    public ElvenkingsHarper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {4}{U}: Target creature can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(
            new CantBeBlockedTargetEffect(),
            new ManaCostsImpl<>("{4}{U}")
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ElvenkingsHarper(final ElvenkingsHarper card) {
        super(card);
    }

    @Override
    public ElvenkingsHarper copy() {
        return new ElvenkingsHarper(this);
    }
}
