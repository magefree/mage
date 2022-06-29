
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardSetInfo;
import mage.cards.LevelerCard;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author North, noxx
 */
public final class TranscendentMaster extends LevelerCard {

    public TranscendentMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.AVATAR);

        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(new LevelUpAbility(new ManaCostsImpl<>("{1}")));

        // Level up {1} ({1}: Put a level counter on this. Level up only as a sorcery.)
        // LEVEL 6-11
        // 6/6
        // Lifelink
        Abilities<Ability> abilities1 = new AbilitiesImpl<>(LifelinkAbility.getInstance());
        // LEVEL 12+
        // 9/9
        // Lifelink
        // Transcendent Master is indestructible.
        Abilities<Ability> abilities2 = new AbilitiesImpl<>(
                LifelinkAbility.getInstance(),
                IndestructibleAbility.getInstance());

        this.addAbilities(LevelerCardBuilder.construct(
                new LevelerCardBuilder.LevelAbility(6, 11, abilities1, 6, 6),
                new LevelerCardBuilder.LevelAbility(12, -1, abilities2, 9, 9)
        ));
        setMaxLevelCounters(12);
    }

    private TranscendentMaster(final TranscendentMaster card) {
        super(card);
    }

    @Override
    public TranscendentMaster copy() {
        return new TranscendentMaster(this);
    }
}
