package mage.cards.f;

import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FreyalisesCharm extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("black spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public FreyalisesCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}{G}");

        // Whenever an opponent casts a black spell, you may pay {G}{G}. If you do, you draw a card.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                new DoIfCostPaid(
                        new DrawCardSourceControllerEffect(1, "you"),
                        new ManaCostsImpl<>("{G}{G}")
                ), filter, false
        ));

        // {G}{G}: Return Freyalise's Charm to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(new ReturnToHandSourceEffect(true), new ManaCostsImpl<>("{G}{G}")));
    }

    private FreyalisesCharm(final FreyalisesCharm card) {
        super(card);
    }

    @Override
    public FreyalisesCharm copy() {
        return new FreyalisesCharm(this);
    }
}
