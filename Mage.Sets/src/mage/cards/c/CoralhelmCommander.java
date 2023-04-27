
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.cards.CardSetInfo;
import mage.cards.LevelerCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author North
 */
public final class CoralhelmCommander extends LevelerCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Merfolk creatures");

    static {
        filter.add(SubType.MERFOLK.getPredicate());
    }

    public CoralhelmCommander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SOLDIER);

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new LevelUpAbility(new ManaCostsImpl<>("{1}")));

        Abilities<Ability> abilities1 = new AbilitiesImpl<>();
        abilities1.add(FlyingAbility.getInstance());

        Abilities<Ability> abilities2 = new AbilitiesImpl<>();
        abilities2.add(FlyingAbility.getInstance());
        abilities2.add(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));

        this.addAbilities(LevelerCardBuilder.construct(
                new LevelerCardBuilder.LevelAbility(2, 3, abilities1, 3, 3),
                new LevelerCardBuilder.LevelAbility(4, -1, abilities2, 4, 4)
        ));
        setMaxLevelCounters(4);
    }

    private CoralhelmCommander(final CoralhelmCommander card) {
        super(card);
    }

    @Override
    public CoralhelmCommander copy() {
        return new CoralhelmCommander(this);
    }
}
