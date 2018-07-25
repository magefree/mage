package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TurnFaceDownSourceCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author NinthWorld
 */
public final class Hellbat extends CardImpl {

    public Hellbat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{R}");
        
        this.subtype.add(SubType.TERRAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Morph {R}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{R}")));

        // {R}, Turn Hellbat face down: Hellbat gains menace until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(new MenaceAbility(), Duration.EndOfTurn), new ManaCostsImpl("{R}"));
        ability.addCost(new TurnFaceDownSourceCost());
        this.addAbility(ability);
    }

    public Hellbat(final Hellbat card) {
        super(card);
    }

    @Override
    public Hellbat copy() {
        return new Hellbat(this);
    }
}