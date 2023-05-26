package mage.cards.g;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GadwickTheWizened extends CardImpl {

    private static final FilterSpell filter
            = new FilterSpell("a blue spell");
    private static final FilterPermanent filter2
            = new FilterNonlandPermanent("nonland permanent an opponent controls");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
        filter2.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public GadwickTheWizened(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{U}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Gadwick, the Wizened enters the battlefield, draw X cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DrawCardSourceControllerEffect(ManacostVariableValue.ETB)
        ));

        // Whenever you cast a blue spell, tap target nonland permanent an opponent controls.
        Ability ability = new SpellCastControllerTriggeredAbility(new TapTargetEffect(), filter, false);
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private GadwickTheWizened(final GadwickTheWizened card) {
        super(card);
    }

    @Override
    public GadwickTheWizened copy() {
        return new GadwickTheWizened(this);
    }
}
