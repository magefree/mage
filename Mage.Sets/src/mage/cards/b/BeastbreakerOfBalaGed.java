
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.LevelerCard;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North, noxx
 */
public final class BeastbreakerOfBalaGed extends LevelerCard {

    public BeastbreakerOfBalaGed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.HUMAN, SubType.WARRIOR);

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Level up {2}{G} ({2}{G}: Put a level counter on this. Level up only as a sorcery.)
        this.addAbility(new LevelUpAbility(new ManaCostsImpl<>("{2}{G}")));

        Abilities<Ability> levelAbilities = new AbilitiesImpl<>();
        levelAbilities.add(TrampleAbility.getInstance());

        this.addAbilities(LevelerCardBuilder.construct(
                // LEVEL 1-3
                //      4/4
                new LevelerCardBuilder.LevelAbility(1, 3, new AbilitiesImpl<>(), 4, 4),
                // LEVEL 1-3
                //      4/4
                //      Trample
                new LevelerCardBuilder.LevelAbility(4, -1, levelAbilities, 6, 6)
        ));
        setMaxLevelCounters(4);
    }

    private BeastbreakerOfBalaGed(final BeastbreakerOfBalaGed card) {
        super(card);
    }

    @Override
    public BeastbreakerOfBalaGed copy() {
        return new BeastbreakerOfBalaGed(this);
    }
}
