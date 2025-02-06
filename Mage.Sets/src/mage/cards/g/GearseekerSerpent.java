package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GearseekerSerpent extends CardImpl {

    public GearseekerSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Affinity for artfifacts
        this.addAbility(new AffinityForArtifactsAbility());

        // {5}{U}: Gearseeker Serpent can't be blocked this turn.
        this.addAbility(new SimpleActivatedAbility(
                new CantBeBlockedSourceEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{5}{U}")
        ));
    }

    private GearseekerSerpent(final GearseekerSerpent card) {
        super(card);
    }

    @Override
    public GearseekerSerpent copy() {
        return new GearseekerSerpent(this);
    }
}
