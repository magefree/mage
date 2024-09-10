package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.GoadAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.filter.predicate.permanent.GoadedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BaelothBarritylEntertainer extends CardImpl {

    private static final FilterPermanent filter = new FilterOpponentsCreaturePermanent();
    private static final FilterCreaturePermanent filter2
            = new FilterCreaturePermanent("a goaded attacking or blocking creature");

    static {
        filter.add(BaelothBarritylEntertainerPredicate.instance);
        filter2.add(Predicates.or(
                AttackingPredicate.instance,
                BlockingPredicate.instance
        ));
        filter2.add(GoadedPredicate.instance);
    }

    public BaelothBarritylEntertainer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Creatures your opponents control with power less than Baeloth Barrityl's power are goaded.
        this.addAbility(new SimpleStaticAbility(new GoadAllEffect(
                Duration.WhileOnBattlefield, filter, false
        ).setText("creatures your opponents control with power less than {this}'s power are goaded")));

        // Whenever a goaded attacking or blocking creature dies, you create a Treasure token.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new CreateTokenEffect(new TreasureToken())
                        .setText("you create a Treasure token"),
                false, filter2
        ));

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private BaelothBarritylEntertainer(final BaelothBarritylEntertainer card) {
        super(card);
    }

    @Override
    public BaelothBarritylEntertainer copy() {
        return new BaelothBarritylEntertainer(this);
    }
}

enum BaelothBarritylEntertainerPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent permanent = input.getSource().getSourcePermanentOrLKI(game);
        return permanent != null && input.getObject().getPower().getValue() < permanent.getPower().getValue();
    }
}