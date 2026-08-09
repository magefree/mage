package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.WolfToken;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.CantAttackSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;

/**
 * @author muz
 */
public final class ChiefWargsCompany extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.WOLF, "other Wolves");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            filter, ComparisonType.FEWER_THAN, 2
    );

    public ChiefWargsCompany(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // This creature can't attack unless you control two or more other Wolves.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
            new CantAttackSourceEffect(Duration.WhileOnBattlefield), condition,
            "{this} can't attack unless you control two or more other Wolves"
        )));

        // At the beginning of your upkeep, create a 2/2 green Wolf creature token.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new WolfToken())));
    }

    private ChiefWargsCompany(final ChiefWargsCompany card) {
        super(card);
    }

    @Override
    public ChiefWargsCompany copy() {
        return new ChiefWargsCompany(this);
    }
}
