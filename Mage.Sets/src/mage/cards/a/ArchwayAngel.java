package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.common.GateYouControlHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class ArchwayAngel extends CardImpl {


    private static final FilterPermanent filter = new FilterControlledPermanent("Gate you control");

    static {
        filter.add(SubType.GATE.getPredicate());
    }

    public ArchwayAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Archway Angel enters the battlefield, you gain 2 life for each Gate you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainLifeEffect(new PermanentsOnBattlefieldCount(filter, 2)));
        ability.addHint(GateYouControlHint.instance);
        this.addAbility(ability);
    }

    private ArchwayAngel(final ArchwayAngel card) {
        super(card);
    }

    @Override
    public ArchwayAngel copy() {
        return new ArchwayAngel(this);
    }
}
