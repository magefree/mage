
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityPairedEffect;
import mage.abilities.keyword.SoulbondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 * @author noxx
 */
public final class Stonewright extends CardImpl {

    private static final String ruleText = "As long as {this} is paired with another creature, each of those creatures has \"{R}: This creature gets +1/+0 until end of turn.\"";

    public Stonewright(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Soulbond
        this.addAbility(new SoulbondAbility());

        // As long as Stonewright is paired with another creature, each of those creatures has "{R}: This creature gets +1/+0 until end of turn."
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{R}"));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityPairedEffect(ability, ruleText)));
    }

    private Stonewright(final Stonewright card) {
        super(card);
    }

    @Override
    public Stonewright copy() {
        return new Stonewright(this);
    }
}
