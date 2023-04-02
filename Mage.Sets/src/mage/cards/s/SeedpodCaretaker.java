package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.target.TargetPermanent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeedpodCaretaker extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(
            SubType.INCUBATOR, "Incubator token you control"
    );

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public SeedpodCaretaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Seedpod Caretaker enters the battlefield, choose one --
        // * Put a +1/+1 counter on target artifact or creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE));

        // * Transform target Incubator token you control.
        ability.addMode(new Mode(new SeedpodCaretakerEffect()).addTarget(new TargetPermanent(filter)));
        this.addAbility(ability);
    }

    private SeedpodCaretaker(final SeedpodCaretaker card) {
        super(card);
    }

    @Override
    public SeedpodCaretaker copy() {
        return new SeedpodCaretaker(this);
    }
}

class SeedpodCaretakerEffect extends OneShotEffect {

    SeedpodCaretakerEffect() {
        super(Outcome.Benefit);
        staticText = "transform target Incubator token you control";
    }

    private SeedpodCaretakerEffect(final SeedpodCaretakerEffect effect) {
        super(effect);
    }

    @Override
    public SeedpodCaretakerEffect copy() {
        return new SeedpodCaretakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(game.getPermanent(getTargetPointer().getFirst(game, source)))
                .filter(Objects::nonNull).map(permanent -> permanent.transform(source, game))
                .orElse(null);
    }
}
