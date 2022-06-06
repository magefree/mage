

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.cards.CardSetInfo;
import mage.cards.LevelerCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki, noxx
 */
public final class GuulDrazAssassin extends LevelerCard {

    public GuulDrazAssassin (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ASSASSIN);
        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new LevelUpAbility(new ManaCostsImpl<>("{1}{B}")));

        Abilities<Ability> abilities1 = new AbilitiesImpl<>();
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(-2, -2, Duration.EndOfTurn), new ManaCostsImpl<>("{B}"));
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new TapSourceCost());
        abilities1.add(ability);

        Abilities<Ability> abilities2 = new AbilitiesImpl<>();
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(-4, -4, Duration.EndOfTurn), new ManaCostsImpl<>("{B}"));
        ability2.addTarget(new TargetCreaturePermanent());
        ability2.addCost(new TapSourceCost());
        abilities2.add(ability2);

        this.addAbilities(LevelerCardBuilder.construct(
                new LevelerCardBuilder.LevelAbility(2, 3, abilities1, 2, 2),
                new LevelerCardBuilder.LevelAbility(4, -1, abilities2, 4, 4)
        ));
        setMaxLevelCounters(4);
    }

    public GuulDrazAssassin (final GuulDrazAssassin card) {
        super(card);
    }

    @Override
    public GuulDrazAssassin copy() {
        return new GuulDrazAssassin(this);
    }

}
