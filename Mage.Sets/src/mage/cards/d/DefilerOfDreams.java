package mage.cards.d;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.MayPay2LifeForColorAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.PermanentPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DefilerOfDreams extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a blue permanent spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
        filter.add(PermanentPredicate.instance);
    }

    public DefilerOfDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As an additional cost to cast blue permanent spells, you may pay 2 life. Those spells cost {U} less to cast if you paid life this way. This effect reduces only the amount of blue mana you pay.
        this.addAbility(new MayPay2LifeForColorAbility(ObjectColor.BLUE));

        // Whenever you cast a blue permanent spell, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter, false
        ));
    }

    private DefilerOfDreams(final DefilerOfDreams card) {
        super(card);
    }

    @Override
    public DefilerOfDreams copy() {
        return new DefilerOfDreams(this);
    }
}
