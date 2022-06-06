

package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.LevelerCard;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki, noxx
 */
public final class IkiralOutrider extends LevelerCard {

    public IkiralOutrider (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        this.addAbility(new LevelUpAbility(new ManaCostsImpl<>("{4}")));

        Abilities<Ability> abilities1 = new AbilitiesImpl<>();
        abilities1.add(VigilanceAbility.getInstance());

        Abilities<Ability> abilities2 = new AbilitiesImpl<>();
        abilities2.add(VigilanceAbility.getInstance());

        this.addAbilities(LevelerCardBuilder.construct(
                new LevelerCardBuilder.LevelAbility(1, 3, abilities1, 2, 6),
                new LevelerCardBuilder.LevelAbility(4, -1, abilities2, 3, 10)
        ));
        setMaxLevelCounters(4);
    }

    public IkiralOutrider (final IkiralOutrider card) {
        super(card);
    }

    @Override
    public IkiralOutrider copy() {
        return new IkiralOutrider(this);
    }

}
