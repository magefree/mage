package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfVryn extends TransformingDoubleFacedCard {

    private static final FilterSpell filter = new FilterSpell("spell you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public InvasionOfVryn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{3}{U}",
                "Overloaded Mage-Ring",
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "U"
        );

        // Invasion of Vryn
        this.getLeftHalfCard().setStartingDefense(4);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Vryn enters the battlefield, draw three cards, then discard a card.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new DrawDiscardControllerEffect(3, 1)));

        // Overloaded Mage-Ring
        // {1}, {T}, Sacrifice Overloaded Mage-Ring: Copy target spell you control. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(
                new CopyTargetStackObjectEffect(false, false, true), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetSpell(filter));
        this.getRightHalfCard().addAbility(ability);
    }

    private InvasionOfVryn(final InvasionOfVryn card) {
        super(card);
    }

    @Override
    public InvasionOfVryn copy() {
        return new InvasionOfVryn(this);
    }
}
