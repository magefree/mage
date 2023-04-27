package mage.cards.h;

import mage.MageInt;
import mage.abilities.AbilitiesImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.ProtectionFromEverythingAbility;
import mage.cards.CardSetInfo;
import mage.cards.LevelerCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Hexdrinker extends LevelerCard {

    private static final FilterCard filter = new FilterCard("instants");

    static {
        filter.add(CardType.INSTANT.getPredicate());
    }

    public Hexdrinker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Level up {1}
        this.addAbility(new LevelUpAbility(new ManaCostsImpl<>("{1}")));

        // LEVEL 3-7
        // 4/4
        // Protection from instants
        // LEVEL 8+
        // 6/6
        // Protection from everything
        this.addAbilities(LevelerCardBuilder.construct(
                new LevelerCardBuilder.LevelAbility(
                        3, 7, new AbilitiesImpl<>(new ProtectionAbility(filter)), 4, 4
                ),
                new LevelerCardBuilder.LevelAbility(
                        8, -1, new AbilitiesImpl<>(new ProtectionFromEverythingAbility()), 6, 6
                )
        ));

        this.setMaxLevelCounters(8);
    }

    private Hexdrinker(final Hexdrinker card) {
        super(card);
    }

    @Override
    public Hexdrinker copy() {
        return new Hexdrinker(this);
    }
}
