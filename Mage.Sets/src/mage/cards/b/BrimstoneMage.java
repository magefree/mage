

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.cards.CardSetInfo;
import mage.cards.LevelerCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Loki, noxx
 */
public final class BrimstoneMage extends LevelerCard {

    public BrimstoneMage (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN, SubType.SHAMAN);
        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new LevelUpAbility(new ManaCostsImpl<>("{3}{R}")));
        Abilities<Ability> abilities1 = new AbilitiesImpl<>();
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        abilities1.add(ability);

        Abilities<Ability> abilities2 = new AbilitiesImpl<>();
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(3), new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        abilities2.add(ability);

        this.addAbilities(LevelerCardBuilder.construct(
                new LevelerCardBuilder.LevelAbility(1, 2, abilities1, 2, 3),
                new LevelerCardBuilder.LevelAbility(3, -1, abilities2, 2, 4)
        ));
        setMaxLevelCounters(3);
    }

    public BrimstoneMage (final BrimstoneMage card) {
        super(card);
    }

    @Override
    public BrimstoneMage copy() {
        return new BrimstoneMage(this);
    }

}
