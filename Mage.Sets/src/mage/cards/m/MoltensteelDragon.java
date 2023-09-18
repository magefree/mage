package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author North
 */
public final class MoltensteelDragon extends CardImpl {

    public MoltensteelDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{R/P}{R/P}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        this.addAbility(new SimpleActivatedAbility(new BoostSourceEffect(
                1, 0, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{R/P}")));
    }

    private MoltensteelDragon(final MoltensteelDragon card) {
        super(card);
    }

    @Override
    public MoltensteelDragon copy() {
        return new MoltensteelDragon(this);
    }
}
