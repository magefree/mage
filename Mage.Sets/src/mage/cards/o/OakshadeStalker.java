package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.PayMoreToCastAsThoughtItHadFlashAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DayboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OakshadeStalker extends CardImpl {

    public OakshadeStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.RANGER);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.m.MoonlitAmbusher.class;

        // You may cast this spell as though it had flash if you pay {2} more to cast it.
        this.addAbility(new PayMoreToCastAsThoughtItHadFlashAbility(this, new ManaCostsImpl<>("{2}")));

        // Daybound
        this.addAbility(new DayboundAbility());
    }

    private OakshadeStalker(final OakshadeStalker card) {
        super(card);
    }

    @Override
    public OakshadeStalker copy() {
        return new OakshadeStalker(this);
    }
}
