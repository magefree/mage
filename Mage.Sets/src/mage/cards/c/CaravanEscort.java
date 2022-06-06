
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.cards.CardSetInfo;
import mage.cards.LevelerCard;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class CaravanEscort extends LevelerCard {

    public CaravanEscort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new LevelUpAbility(new ManaCostsImpl<>("{2}")));

        AbilitiesImpl<Ability> levelAbilities = new AbilitiesImpl<>(FirstStrikeAbility.getInstance());
        this.addAbilities(LevelerCardBuilder.construct(
                new LevelerCardBuilder.LevelAbility(1, 4, new AbilitiesImpl<>(), 2, 2),
                new LevelerCardBuilder.LevelAbility(5, -1, levelAbilities, 5, 5)
        ));

        setMaxLevelCounters(5);
    }

    private CaravanEscort(final CaravanEscort card) {
        super(card);
    }

    @Override
    public CaravanEscort copy() {
        return new CaravanEscort(this);
    }
}
