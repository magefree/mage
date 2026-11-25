package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author muz
 */
public final class UhuraXenolinguist extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Human creature");

    static {
        filter.add(Predicates.not(SubType.HUMAN.getPredicate()));
    }

    public UhuraXenolinguist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever a non-Human creature enters, you may pay {1}. If you do, draw a card.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new DoIfCostPaid(
            new DrawCardSourceControllerEffect(1), new GenericManaCost(1)
        ), filter));
    }

    private UhuraXenolinguist(final UhuraXenolinguist card) {
        super(card);
    }

    @Override
    public UhuraXenolinguist copy() {
        return new UhuraXenolinguist(this);
    }
}
