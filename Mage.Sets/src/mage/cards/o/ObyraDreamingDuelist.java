package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ObyraDreamingDuelist extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.FAERIE, "another Faerie");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ObyraDreamingDuelist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever another Faerie enters the battlefield under your control, each opponent loses 1 life.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new LoseLifeOpponentsEffect(1),
                filter, false);
        this.addAbility(ability);
    }

    private ObyraDreamingDuelist(final ObyraDreamingDuelist card) {
        super(card);
    }

    @Override
    public ObyraDreamingDuelist copy() {
        return new ObyraDreamingDuelist(this);
    }
}
