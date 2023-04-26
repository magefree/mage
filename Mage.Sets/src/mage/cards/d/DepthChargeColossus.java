package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.PrototypeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DepthChargeColossus extends CardImpl {

    public DepthChargeColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{9}");

        this.subtype.add(SubType.DREADNOUGHT);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // Prototype {4}{U}{U} -- 6/6
        this.addAbility(new PrototypeAbility(this, "{4}{U}{U}", 6, 6));

        // Depth Charge Colossus doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepSourceEffect()));

        // {3}: Untap Depth Charge Colossus.
        this.addAbility(new SimpleActivatedAbility(new UntapSourceEffect(), new GenericManaCost(3)));
    }

    private DepthChargeColossus(final DepthChargeColossus card) {
        super(card);
    }

    @Override
    public DepthChargeColossus copy() {
        return new DepthChargeColossus(this);
    }
}
