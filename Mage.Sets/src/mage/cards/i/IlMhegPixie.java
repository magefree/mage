package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IlMhegPixie extends CardImpl {

    public IlMhegPixie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature attacks, surveil 1.
        this.addAbility(new AttacksTriggeredAbility(new SurveilEffect(1)));
    }

    private IlMhegPixie(final IlMhegPixie card) {
        super(card);
    }

    @Override
    public IlMhegPixie copy() {
        return new IlMhegPixie(this);
    }
}
