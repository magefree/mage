

package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DeathtouchAbility;
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
public final class NirkanaCutthroat extends LevelerCard {

    public NirkanaCutthroat (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARRIOR);
        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.addAbility(new LevelUpAbility(new ManaCostsImpl<>("{2}{B}")));

        Abilities<Ability> abilities1 = new AbilitiesImpl<>();
        abilities1.add(DeathtouchAbility.getInstance());

        Abilities<Ability> abilities2 = new AbilitiesImpl<>();
        abilities2.add(FirstStrikeAbility.getInstance());
        abilities2.add(DeathtouchAbility.getInstance());

        this.addAbilities(LevelerCardBuilder.construct(
                new LevelerCardBuilder.LevelAbility(1, 2, abilities1, 4, 3),
                new LevelerCardBuilder.LevelAbility(3, -1, abilities2, 5, 4)
        ));
        setMaxLevelCounters(3);
    }

    public NirkanaCutthroat (final NirkanaCutthroat card) {
        super(card);
    }

    @Override
    public NirkanaCutthroat copy() {
        return new NirkanaCutthroat(this);
    }

}
