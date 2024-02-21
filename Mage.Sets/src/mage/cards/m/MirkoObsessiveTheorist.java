package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.SurveilTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class MirkoObsessiveTheorist extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature with power less than this creature's");

    static {
        filter.add(MirkoObsessiveTheoristPredicate.instance);
    }

    public MirkoObsessiveTheorist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE, SubType.DETECTIVE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever you surveil, put a +1/+1 counter on Mirko, Obsessive Theorist.
        this.addAbility(new SurveilTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));

        // At the beginning of your end step, you may return target creature card with power less than Mirko's from your graveyard to the battlefield with a finality counter on it.
        Ability ability = new BeginningOfYourEndStepTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(CounterType.FINALITY.createInstance())
                        .setText("you may return target creature card with power less than {this}'s from your graveyard to the " +
                                "battlefield with a finality counter on it. <i>(If it would die, exile it instead.)</i>"),true
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private MirkoObsessiveTheorist(final MirkoObsessiveTheorist card) {
        super(card);
    }

    @Override
    public MirkoObsessiveTheorist copy() {
        return new MirkoObsessiveTheorist(this);
    }
}

enum MirkoObsessiveTheoristPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        Permanent sourcePermanent = input.getSource().getSourcePermanentOrLKI(game);
        return sourcePermanent != null && input.getObject().getPower().getValue() <= sourcePermanent.getPower().getValue();
    }
}
