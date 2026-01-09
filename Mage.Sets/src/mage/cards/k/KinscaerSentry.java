package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KinscaerSentry extends CardImpl {

    private static final FilterCard filter = new FilterCard(
            "creature card with mana value less than or equal to the number of attacking creatures you control"
    );

    static {
        filter.add(KinscaerSentryPredicate.instance);
    }

    public KinscaerSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever this creature attacks, you may put a creature card with mana value X or less from your hand onto the battlefield tapped and attacking, where X is the number of attacking creatures you control.
        this.addAbility(new AttacksTriggeredAbility(new PutCardFromHandOntoBattlefieldEffect(
                filter, false, true, true
        ).setText("you may put a creature card with mana value X or less from your hand onto the battlefield " +
                "tapped and attacking, where X is the number of attacking creatures you control")).addHint(KinscaerSentryPredicate.getHint()));
    }

    private KinscaerSentry(final KinscaerSentry card) {
        super(card);
    }

    @Override
    public KinscaerSentry copy() {
        return new KinscaerSentry(this);
    }
}

enum KinscaerSentryPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;
    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(AttackingPredicate.instance);
    }

    private static final Hint hint = new ValueHint("Attacking creatures you control", new PermanentsOnBattlefieldCount(filter));

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return input.getObject().getManaValue() <= game.getBattlefield().count(filter, input.getPlayerId(), input.getSource(), game);
    }
}
