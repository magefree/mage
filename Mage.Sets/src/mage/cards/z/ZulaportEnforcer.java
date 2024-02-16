
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.cards.CardSetInfo;
import mage.cards.LevelerCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author North, noxx
 */
public final class ZulaportEnforcer extends LevelerCard {

    private static final FilterCreaturePermanent notBlackCreatures = new FilterCreaturePermanent("except by black creatures");

    static {
        notBlackCreatures.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public ZulaportEnforcer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.HUMAN, SubType.WARRIOR);

        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new LevelUpAbility(new ManaCostsImpl<>("{4}")));

        // LEVEL 1-2:  3/3
        // LEVEL 3+: 5/5
        // Zulaport Enforcer can't be blocked except by black creatures.
        Abilities<Ability> levelAbilities = new AbilitiesImpl<>();
        levelAbilities.add(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(notBlackCreatures, Duration.WhileOnBattlefield)));

        this.addAbilities(LevelerCardBuilder.construct(
                new LevelerCardBuilder.LevelAbility(1, 2, new AbilitiesImpl<>(), 3, 3),
                new LevelerCardBuilder.LevelAbility(3, -1, levelAbilities, 5, 5)
        ));
        setMaxLevelCounters(3);
    }

    private ZulaportEnforcer(final ZulaportEnforcer card) {
        super(card);
    }

    @Override
    public ZulaportEnforcer copy() {
        return new ZulaportEnforcer(this);
    }
}
