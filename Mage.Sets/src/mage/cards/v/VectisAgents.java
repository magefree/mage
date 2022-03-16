package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Loki
 */
public final class VectisAgents extends CardImpl {

    public VectisAgents(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{U}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // {U}{B}: Vectis Agents gets -2/-0 until end of turn and can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(new BoostSourceEffect(
                -2, -0, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{U}{B}"));
        ability.addEffect(new CantBeBlockedSourceEffect(Duration.EndOfTurn).setText("and can't be blocked this turn"));
        this.addAbility(ability);
    }

    private VectisAgents(final VectisAgents card) {
        super(card);
    }

    @Override
    public VectisAgents copy() {
        return new VectisAgents(this);
    }
}
