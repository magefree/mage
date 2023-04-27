package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.SpectacleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Hackrobat extends CardImpl {

    public Hackrobat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Spectacle {B}{R}
        this.addAbility(new SpectacleAbility(this, new ManaCostsImpl<>("{B}{R}")));

        // {B}: Hackrobat gains deathtouch until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(
                        DeathtouchAbility.getInstance(),
                        Duration.EndOfTurn
                ), new ColoredManaCost(ColoredManaSymbol.B)
        ));

        // {R}: Hackrobat gets +2/-2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(2, -2, Duration.EndOfTurn),
                new ColoredManaCost(ColoredManaSymbol.R)
        ));
    }

    private Hackrobat(final Hackrobat card) {
        super(card);
    }

    @Override
    public Hackrobat copy() {
        return new Hackrobat(this);
    }
}
