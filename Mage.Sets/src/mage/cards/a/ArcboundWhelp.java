package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ModularAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArcboundWhelp extends CardImpl {

    public ArcboundWhelp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {R}: Arcbound Whelp gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostSourceEffect(
                1, 0, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{R}")));

        // Modular 1
        this.addAbility(new ModularAbility(this, 2));
    }

    private ArcboundWhelp(final ArcboundWhelp card) {
        super(card);
    }

    @Override
    public ArcboundWhelp copy() {
        return new ArcboundWhelp(this);
    }
}
