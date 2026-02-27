package mage.cards.m;

import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MonsterMashup extends CardImpl {

    public MonsterMashup(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.WEREWOLF);
        this.subtype.add(SubType.FISH);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility());
    }

    private MonsterMashup(final MonsterMashup card) {
        super(card);
    }

    @Override
    public MonsterMashup copy() {
        return new MonsterMashup(this);
    }
}
