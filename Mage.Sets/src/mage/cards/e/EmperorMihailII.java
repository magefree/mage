package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.game.permanent.token.MerfolkToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmperorMihailII extends CardImpl {

    private static final FilterCard filter = new FilterCard("cast Merfolk spells");
    private static final FilterSpell filter2 = new FilterSpell("a Merfolk spell");

    static {
        filter.add(SubType.MERFOLK.getPredicate());
        filter2.add(SubType.MERFOLK.getPredicate());
    }

    public EmperorMihailII(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may cast Merfolk spells from the top of your library.
        this.addAbility(new SimpleStaticAbility(new PlayTheTopCardEffect(
                TargetController.YOU, filter, false
        )));

        // Whenever you cast a Merfolk spell, you may pay {1}. If you do, create a 1/1 blue Merfolk creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DoIfCostPaid(
                new CreateTokenEffect(new MerfolkToken()), new GenericManaCost(1)
        ), filter2, false));
    }

    private EmperorMihailII(final EmperorMihailII card) {
        super(card);
    }

    @Override
    public EmperorMihailII copy() {
        return new EmperorMihailII(this);
    }
}
