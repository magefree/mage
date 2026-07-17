package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class HitMonkey extends CardImpl {

    public HitMonkey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MONKEY);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility().setRuleAtTheTop(true));

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());
    }

    private HitMonkey(final HitMonkey card) {
        super(card);
    }

    @Override
    public HitMonkey copy() {
        return new HitMonkey(this);
    }
}
