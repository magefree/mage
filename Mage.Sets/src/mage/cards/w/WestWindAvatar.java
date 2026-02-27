package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.watchers.common.RevoltWatcher;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class WestWindAvatar extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("a token or a land");

    static {
        filter.add(Predicates.or(
                TokenPredicate.TRUE,
                CardType.LAND.getPredicate()
        ));
    }

    public WestWindAvatar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever this creature enters or attacks, you may sacrifice a token or a land. If you do, you gain 3 life.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DoIfCostPaid(
            new GainLifeEffect(3),
            new SacrificeTargetCost(filter)
        )));

        // Disappear -- At the beginning of your end step, if a permanent left the battlefield under your control this turn, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1))
            .withInterveningIf(RevoltCondition.instance)
            .addHint(RevoltCondition.getHint())
            .setAbilityWord(AbilityWord.DISAPPEAR), new RevoltWatcher());
    }

    private WestWindAvatar(final WestWindAvatar card) {
        super(card);
    }

    @Override
    public WestWindAvatar copy() {
        return new WestWindAvatar(this);
    }
}
