

package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.cards.CardSetInfo;
import mage.cards.LevelerCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com, noxx
 */
public final class EnclaveCryptologist extends LevelerCard {

    public EnclaveCryptologist (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.color.setBlue(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        this.addAbility(new LevelUpAbility(new ManaCostsImpl<>("{1}{U}")));

        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawDiscardControllerEffect(), new TapSourceCost());
        Abilities<Ability> abilities1 = new AbilitiesImpl<>(ability);

        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new TapSourceCost());
        Abilities<Ability> abilities2 = new AbilitiesImpl<>(ability);

        this.addAbilities(LevelerCardBuilder.construct(
                new LevelerCardBuilder.LevelAbility(1, 2, abilities1, 0, 1),
                new LevelerCardBuilder.LevelAbility(3, -1, abilities2, 0, 1)
        ));
        setMaxLevelCounters(3);
    }

    public EnclaveCryptologist (final EnclaveCryptologist card) {
        super(card);
    }

    @Override
    public EnclaveCryptologist copy() {
        return new EnclaveCryptologist(this);
    }

}
