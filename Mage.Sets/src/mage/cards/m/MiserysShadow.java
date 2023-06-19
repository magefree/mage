package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.replacement.CreaturesOppCtrlAreExiledOnDeathReplacementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class MiserysShadow extends CardImpl {

    public MiserysShadow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.SHADE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // If a creature an opponent controls would die, exile it instead.
        this.addAbility(new SimpleStaticAbility(new CreaturesOppCtrlAreExiledOnDeathReplacementEffect()));

        // {1}: Misery's Shadow gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn), new GenericManaCost(1)));
    }

    private MiserysShadow(final MiserysShadow card) {
        super(card);
    }

    @Override
    public MiserysShadow copy() {
        return new MiserysShadow(this);
    }
}
