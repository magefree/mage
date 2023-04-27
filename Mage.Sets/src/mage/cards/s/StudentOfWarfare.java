

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.cards.CardSetInfo;
import mage.cards.LevelerCard;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki, noxx
 */
public final class StudentOfWarfare extends LevelerCard {

    public StudentOfWarfare (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new LevelUpAbility(new ManaCostsImpl<>("{W}")));
        Abilities<Ability> abilities1 = new AbilitiesImpl<>();
        abilities1.add(FirstStrikeAbility.getInstance());

        Abilities<Ability> abilities2 = new AbilitiesImpl<>();
        abilities2.add(DoubleStrikeAbility.getInstance());

        this.addAbilities(LevelerCardBuilder.construct(
                new LevelerCardBuilder.LevelAbility(2, 6, abilities1, 3, 3),
                new LevelerCardBuilder.LevelAbility(7, -1, abilities2, 4, 4)
        ));
        setMaxLevelCounters(7);
    }

    public StudentOfWarfare (final StudentOfWarfare card) {
        super(card);
    }

    @Override
    public StudentOfWarfare copy() {
        return new StudentOfWarfare(this);
    }

}
