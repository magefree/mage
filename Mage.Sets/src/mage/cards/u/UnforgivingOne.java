package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnforgivingOne extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard(
            "creature card with mana value less than or equal " +
                    "to the number of modified creatures you control"
    );

    static {
        filter.add(UnforgivingOnePredicate.instance);
    }

    public UnforgivingOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Unforgiving One attacks, return target creature card with mana value X or less from your graveyard to the battlefield, where X is the number of modified creatures you control.
        Ability ability = new AttacksTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setText("return target creature card with mana value X or less from your graveyard " +
                        "to the battlefield, where X is the number of modified creatures you control"));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability.addHint(UnforgivingOnePredicate.getHint()));
    }

    private UnforgivingOne(final UnforgivingOne card) {
        super(card);
    }

    @Override
    public UnforgivingOne copy() {
        return new UnforgivingOne(this);
    }
}

enum UnforgivingOnePredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;
    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(ModifiedPredicate.instance);
    }

    private static final Hint hint = new ValueHint(
            "Modified creatures you control", new PermanentsOnBattlefieldCount(filter)
    );

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return input.getObject().getManaValue()
                <= game.getBattlefield().count(filter, input.getSourceId(), input.getPlayerId(), game);
    }
}