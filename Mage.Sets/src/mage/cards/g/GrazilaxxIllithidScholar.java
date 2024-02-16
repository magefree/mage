package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedAllTriggeredAbility;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class GrazilaxxIllithidScholar extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public GrazilaxxIllithidScholar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever a creature you control becomes blocked, you may return it to its owner's hand.
        this.addAbility(new BecomesBlockedAllTriggeredAbility(
                new ReturnToHandTargetEffect().setText("return it to its owner's hand"), true, filter, true
        ));

        // Whenever one or more creatures you control deal combat damage to a player, draw a card.
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private GrazilaxxIllithidScholar(final GrazilaxxIllithidScholar card) {
        super(card);
    }

    @Override
    public GrazilaxxIllithidScholar copy() {
        return new GrazilaxxIllithidScholar(this);
    }
}
