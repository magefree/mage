package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.hint.common.DomainHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NishobaBrawler extends CardImpl {

    public NishobaBrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Domain — Nishoba Brawler's power is equal to the number of basic land types among lands you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerSourceEffect(DomainValue.REGULAR)
        ).setAbilityWord(AbilityWord.DOMAIN).addHint(DomainHint.instance));
    }

    private NishobaBrawler(final NishobaBrawler card) {
        super(card);
    }

    @Override
    public NishobaBrawler copy() {
        return new NishobaBrawler(this);
    }
}
