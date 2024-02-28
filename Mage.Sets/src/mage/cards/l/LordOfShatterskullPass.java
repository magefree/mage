package mage.cards.l;

import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageAllControlledTargetEffect;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.cards.CardSetInfo;
import mage.cards.LevelerCard;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class LordOfShatterskullPass extends LevelerCard {

    public LordOfShatterskullPass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.SHAMAN);

        this.color.setRed(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Level up {1}{R}
        this.addAbility(new LevelUpAbility(new ManaCostsImpl<>("{1}{R}")));
        // LEVEL 1-5
        // 6/6
        Abilities<Ability> abilities1 = new AbilitiesImpl<>();
        // LEVEL 6+
        // 6/6
        // Whenever Lord of Shatterskull Pass attacks, it deals 6 damage to each creature defending player controls.
        Abilities<Ability> abilities2 = new AbilitiesImpl<>();
        abilities2.add(new AttacksTriggeredAbility(new DamageAllControlledTargetEffect(6)
                .setText("it deals 6 damage to each creature defending player controls"),
                false, null, SetTargetPointer.PLAYER));

        this.addAbilities(LevelerCardBuilder.construct(
                new LevelerCardBuilder.LevelAbility(1, 5, abilities1, 6, 6),
                new LevelerCardBuilder.LevelAbility(6, -1, abilities2, 6, 6)));
        setMaxLevelCounters(6);
    }

    private LordOfShatterskullPass(final LordOfShatterskullPass card) {
        super(card);
    }

    @Override
    public LordOfShatterskullPass copy() {
        return new LordOfShatterskullPass(this);
    }
}
