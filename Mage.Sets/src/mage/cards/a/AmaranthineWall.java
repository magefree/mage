package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 *
 * @author Rystan
 */
public final class AmaranthineWall extends CardImpl {

    public AmaranthineWall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(6);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // {2}: Amaranthine Wall gains indestructible until end of turn.
        GainAbilitySourceEffect effect = new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn);
        SimpleActivatedAbility ability = new SimpleActivatedAbility(effect, new ManaCostsImpl<>("{2}"));
        this.addAbility(ability);
    }

    private AmaranthineWall(final AmaranthineWall card) {
        super(card);
    }

    @Override
    public AmaranthineWall copy() {
        return new AmaranthineWall(this);
    }
}

