

package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.cards.CardSetInfo;
import mage.cards.LevelerCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Loki, noxx
 */
public final class NullChampion extends LevelerCard {

    public NullChampion (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WARRIOR);
        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new LevelUpAbility(new ManaCostsImpl<>("{3}")));

        Abilities<Ability> abilities1 = new AbilitiesImpl<>();

        Abilities<Ability> abilities2 = new AbilitiesImpl<>();
        abilities2.add(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{B}")));

        this.addAbilities(LevelerCardBuilder.construct(
                new LevelerCardBuilder.LevelAbility(1, 3, abilities1, 4, 2),
                new LevelerCardBuilder.LevelAbility(4, -1, abilities2, 7, 3)
        ));
        setMaxLevelCounters(4);
    }

    public NullChampion (final NullChampion card) {
        super(card);
    }

    @Override
    public NullChampion copy() {
        return new NullChampion(this);
    }

}
