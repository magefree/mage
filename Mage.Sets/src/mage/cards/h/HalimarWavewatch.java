
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.IslandwalkAbility;
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
public final class HalimarWavewatch extends LevelerCard {

    public HalimarWavewatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SOLDIER);

        this.color.setBlue(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        this.addAbility(new LevelUpAbility(new ManaCostsImpl<>("{2}")));

        Abilities<Ability> levelAbilities = new AbilitiesImpl<>();
        levelAbilities.add(new IslandwalkAbility());

        this.addAbilities(LevelerCardBuilder.construct(
                new LevelerCardBuilder.LevelAbility(1, 4, new AbilitiesImpl<>(), 0, 6),
                new LevelerCardBuilder.LevelAbility(5, -1, levelAbilities, 6, 6)
        ));
        setMaxLevelCounters(5);
    }

    private HalimarWavewatch(final HalimarWavewatch card) {
        super(card);
    }

    @Override
    public HalimarWavewatch copy() {
        return new HalimarWavewatch(this);
    }
}
