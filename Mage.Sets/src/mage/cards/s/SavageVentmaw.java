package mage.cards.s;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.mana.UntilEndOfTurnManaEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class SavageVentmaw extends CardImpl {

    public SavageVentmaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{G}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Savage Ventmaw attacks, add {R}{R}{R}{G}{G}{G}. Until end of turn, you don't lose this mana as steps and phases end.
        this.addAbility(new AttacksTriggeredAbility(new UntilEndOfTurnManaEffect(
                new Mana(0, 0, 0, 3, 3, 0, 0, 0)
        ), false));
    }

    private SavageVentmaw(final SavageVentmaw card) {
        super(card);
    }

    @Override
    public SavageVentmaw copy() {
        return new SavageVentmaw(this);
    }
}
