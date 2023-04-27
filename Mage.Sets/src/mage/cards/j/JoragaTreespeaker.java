

package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.LevelerCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;


/**
 *
 * @author BetaSteward_at_googlemail.com, noxx
 */
public final class JoragaTreespeaker extends LevelerCard {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Elves");

    static {
        filter.add(SubType.ELF.getPredicate());
    }

    public JoragaTreespeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Level up {1}{G} ({1}{G}: Put a level counter on this. Level up only as a sorcery.)
        this.addAbility(new LevelUpAbility(new ManaCostsImpl<>("{1}{G}")));

        // LEVEL 1-4
        // 1/2
        // {T}: Add {G}{G}.
        Abilities<Ability> abilities1 = new AbilitiesImpl<>();
        abilities1.add(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(2), new TapSourceCost()));

        // LEVEL 5+
        // 1/4
        // Elves you control have "{T}: Add {G}{G}."
        Abilities<Ability> abilities2 = new AbilitiesImpl<>();
        abilities2.add(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(2), new TapSourceCost()),
                Duration.WhileOnBattlefield, filter)));

        this.addAbilities(LevelerCardBuilder.construct(
                new LevelerCardBuilder.LevelAbility(1, 4, abilities1, 1, 2),
                new LevelerCardBuilder.LevelAbility(5, -1, abilities2, 1, 4)
        ));
        setMaxLevelCounters(5);
    }

    private JoragaTreespeaker(final JoragaTreespeaker card) {
        super(card);
    }

    @Override
    public JoragaTreespeaker copy() {
        return new JoragaTreespeaker(this);
    }

}
