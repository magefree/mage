package mage.cards.e;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.ProliferatedControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class EzuriStalkerOfSpheres extends CardImpl {

    public EzuriStalkerOfSpheres(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Ezuri, Stalker of Spheres enters the battlefield, you may pay {3}. If you do, proliferate twice.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new ProliferateEffect(false), new GenericManaCost(3)
        ).addEffect(new ProliferateEffect().setText("twice"))));

        // Whenever you proliferate, draw a card.
        this.addAbility(new ProliferatedControllerTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private EzuriStalkerOfSpheres(final EzuriStalkerOfSpheres card) {
        super(card);
    }

    @Override
    public EzuriStalkerOfSpheres copy() {
        return new EzuriStalkerOfSpheres(this);
    }
}
