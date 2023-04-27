
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.LevelerCard;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North, noxx
 */
public final class KnightOfCliffhaven extends LevelerCard {

    public KnightOfCliffhaven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.KNIGHT);

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new LevelUpAbility(new ManaCostsImpl<>("{3}")));

        Abilities<Ability> abilities1 = new AbilitiesImpl<>();
        abilities1.add(FlyingAbility.getInstance());

        Abilities<Ability> abilities2 = new AbilitiesImpl<>();
        abilities2.add(FlyingAbility.getInstance());
        abilities2.add(VigilanceAbility.getInstance());

        this.addAbilities(LevelerCardBuilder.construct(
                new LevelerCardBuilder.LevelAbility(1, 3, abilities1, 2, 3),
                new LevelerCardBuilder.LevelAbility(4, -1, abilities2, 4, 4)
        ));
        setMaxLevelCounters(4);
    }

    private KnightOfCliffhaven(final KnightOfCliffhaven card) {
        super(card);
    }

    @Override
    public KnightOfCliffhaven copy() {
        return new KnightOfCliffhaven(this);
    }
}
