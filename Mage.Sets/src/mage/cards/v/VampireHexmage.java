
package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.counter.RemoveAllCountersPermanentTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Loki, nantuko
 */
public final class VampireHexmage extends CardImpl {

    public VampireHexmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(FirstStrikeAbility.getInstance());

        // Sacrifice Vampire Hexmage: Remove all counters from target permanent.
        Ability ability = new SimpleActivatedAbility(
                new RemoveAllCountersPermanentTargetEffect(),
                new SacrificeSourceCost()
        );
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private VampireHexmage(final VampireHexmage card) {
        super(card);
    }

    @Override
    public VampireHexmage copy() {
        return new VampireHexmage(this);
    }
}