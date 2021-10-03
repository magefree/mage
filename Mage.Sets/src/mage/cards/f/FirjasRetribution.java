package mage.cards.f;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.AngelWarriorVigilanceToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class FirjasRetribution extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature with power less than this creature's power");
    private static final FilterControlledPermanent filter2
            = new FilterControlledPermanent(SubType.ANGEL, "Angels you control");

    static {
        filter.add(FirjasRetributionPredicate.instance);
    }

    public FirjasRetribution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}{B}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_III);

        // I — Create a 4/4 white Angel Warrior creature token with flying and vigilance.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new CreateTokenEffect(new AngelWarriorVigilanceToken()));

        // II — Until end of turn, Angels you control gain "{T}: Destroy target creature with less power than this creature."
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new GainAbilityAllEffect(
                ability, Duration.EndOfTurn, filter2
        ).setText("until end of turn, Angels you control gain \"{T}: Destroy target creature with power less than this creature's power.\""));

        // III — Angels you control gain double strike until end of turn.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new GainAbilityAllEffect(
                DoubleStrikeAbility.getInstance(), Duration.EndOfTurn, filter2
        ));
        this.addAbility(sagaAbility);
    }

    private FirjasRetribution(final FirjasRetribution card) {
        super(card);
    }

    @Override
    public FirjasRetribution copy() {
        return new FirjasRetribution(this);
    }
}

enum FirjasRetributionPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(input.getSourceId());
        return permanent != null && input.getObject().getPower().getValue() < permanent.getPower().getValue();
    }
}
