
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.cards.CardSetInfo;
import mage.cards.LevelerCard;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North, noxx
 */
public final class SkywatcherAdept extends LevelerCard {

    public SkywatcherAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new LevelUpAbility(new ManaCostsImpl<>("{3}")));

        Abilities<Ability> abilities1 = new AbilitiesImpl<>();
        abilities1.add(FlyingAbility.getInstance());

        Abilities<Ability> abilities2 = new AbilitiesImpl<>();
        abilities2.add(FlyingAbility.getInstance());

        this.addAbilities(LevelerCardBuilder.construct(
                new LevelerCardBuilder.LevelAbility(1, 2, abilities1, 2, 2),
                new LevelerCardBuilder.LevelAbility(3, -1, abilities2, 4, 2)
        ));
        setMaxLevelCounters(3);
    }

    private SkywatcherAdept(final SkywatcherAdept card) {
        super(card);
    }

    @Override
    public SkywatcherAdept copy() {
        return new SkywatcherAdept(this);
    }
}
