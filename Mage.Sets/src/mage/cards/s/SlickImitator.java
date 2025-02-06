package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SlickImitator extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public SlickImitator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // Max speed -- {1}, Sacrifice this creature: Copy target spell you control. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(new CopyTargetStackObjectEffect(), new GenericManaCost(1));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(new MaxSpeedAbility(ability));
    }

    private SlickImitator(final SlickImitator card) {
        super(card);
    }

    @Override
    public SlickImitator copy() {
        return new SlickImitator(this);
    }
}
