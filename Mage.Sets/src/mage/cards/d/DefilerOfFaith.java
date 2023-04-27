package mage.cards.d;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.MayPay2LifeForColorAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.PermanentPredicate;
import mage.game.permanent.token.SoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DefilerOfFaith extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a white permanent spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        filter.add(PermanentPredicate.instance);
    }

    public DefilerOfFaith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // As an additional cost to cast white permanent spells, you may pay 2 life. Those spells cost {W} less to cast if you paid life this way. This effect reduces only the amount of white mana you pay.
        this.addAbility(new MayPay2LifeForColorAbility(ObjectColor.WHITE));

        // Whenever you cast a white permanent spell, create a 1/1 white Soldier creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new SoldierToken()), filter, false
        ));
    }

    private DefilerOfFaith(final DefilerOfFaith card) {
        super(card);
    }

    @Override
    public DefilerOfFaith copy() {
        return new DefilerOfFaith(this);
    }
}
