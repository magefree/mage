package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class LamplighterOfSelhoff extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.ZOMBIE, "you control another Zombie");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public LamplighterOfSelhoff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // When Lamplighter of Selhoff enters the battlefield, if you control another Zombie, you may a draw card. If you do, discard a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DrawDiscardControllerEffect(1, 1, true)
        ).withInterveningIf(condition));
    }

    private LamplighterOfSelhoff(final LamplighterOfSelhoff card) {
        super(card);
    }

    @Override
    public LamplighterOfSelhoff copy() {
        return new LamplighterOfSelhoff(this);
    }
}
