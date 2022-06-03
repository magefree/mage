
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.cards.CardSetInfo;
import mage.cards.LevelerCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.ElephantToken;

/**
 * @author North, noxx
 */
public final class KazanduTuskcaller extends LevelerCard {

    public KazanduTuskcaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new LevelUpAbility(new ManaCostsImpl<>("{1}{G}")));

        Abilities<Ability> abilities1 = new AbilitiesImpl<>();
        abilities1.add(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new CreateTokenEffect(new ElephantToken()),
                new TapSourceCost()));

        Abilities<Ability> abilities2 = new AbilitiesImpl<>();
        abilities2.add(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new CreateTokenEffect(new ElephantToken(), 2),
                new TapSourceCost()));

        this.addAbilities(LevelerCardBuilder.construct(
                new LevelerCardBuilder.LevelAbility(2, 5, abilities1, 1, 1),
                new LevelerCardBuilder.LevelAbility(6, -1, abilities2, 1, 1)
        ));
        setMaxLevelCounters(6);
    }

    private KazanduTuskcaller(final KazanduTuskcaller card) {
        super(card);
    }

    @Override
    public KazanduTuskcaller copy() {
        return new KazanduTuskcaller(this);
    }
}
