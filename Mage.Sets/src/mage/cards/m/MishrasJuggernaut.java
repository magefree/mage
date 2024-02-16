package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MishrasJuggernaut extends CardImpl {

    public MishrasJuggernaut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.JUGGERNAUT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Mishra's Juggernaut attacks each combat if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // Unearth {5}{R}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{5}{R}")));
    }

    private MishrasJuggernaut(final MishrasJuggernaut card) {
        super(card);
    }

    @Override
    public MishrasJuggernaut copy() {
        return new MishrasJuggernaut(this);
    }
}
