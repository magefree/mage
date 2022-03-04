package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class BlindingSouleater extends CardImpl {

    public BlindingSouleater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {W/P},{T}: Tap target creature. ( can be paid with either or 2 life.)
        SimpleActivatedAbility ability = new SimpleActivatedAbility(new TapTargetEffect(), new ManaCostsImpl<>("{W/P}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BlindingSouleater(final BlindingSouleater card) {
        super(card);
    }

    @Override
    public BlindingSouleater copy() {
        return new BlindingSouleater(this);
    }
}
