
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardSetInfo;
import mage.cards.LevelerCard;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North, noxx
 */
public final class HadaSpyPatrol extends LevelerCard {

    public HadaSpyPatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Level up 2Blue ({U}{U}: Put a level counter on this. Level up only as a sorcery.)
        this.addAbility(new LevelUpAbility(new ManaCostsImpl<>("{2}{U}")));

        // LEVEL 1-2
        // 2/2
        // Hada Spy Patrol can't be blocked.
        Abilities<Ability> abilities1 = new AbilitiesImpl<>();
        abilities1.add(new CantBeBlockedSourceAbility());

        // LEVEL 3+
        // 3/3
        // Shroud (This creature can't be the target of spells or abilities.)
        // Hada Spy Patrol can't be blocked.
        Abilities<Ability> abilities2 = new AbilitiesImpl<>();
        abilities2.add(ShroudAbility.getInstance());
        abilities2.add(new CantBeBlockedSourceAbility());
        
        this.addAbilities(LevelerCardBuilder.construct(
                new LevelerCardBuilder.LevelAbility(1, 2, abilities1, 2, 2),
                new LevelerCardBuilder.LevelAbility(3, -1, abilities2, 3, 3)
        ));
        setMaxLevelCounters(3);
    }

    private HadaSpyPatrol(final HadaSpyPatrol card) {
        super(card);
    }

    @Override
    public HadaSpyPatrol copy() {
        return new HadaSpyPatrol(this);
    }
}
