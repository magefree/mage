package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.keyword.CraftAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BraidedNet extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterNonlandPermanent("another target nonland permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public BraidedNet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{2}{U}",
                "Braided Quipu",
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "U");

        // Braided Net
        // Braided Net enters the battlefield with three net counters on it.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.NET.createInstance(3)),
                "with three net counters on it"
        ));

        // {T}, Remove a net counter from Braided Net: Tap another target nonland permanent. Its activated abilities can't be activated for as long as it remains tapped.
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect(), new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.NET.createInstance()));
        ability.addEffect(new BraidedNetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.getLeftHalfCard().addAbility(ability);

        // Craft with artifact {1}{U}
        this.getLeftHalfCard().addAbility(new CraftAbility("{1}{U}"));

        // Braided Quipu
        // {3}{U}, {T}: Draw a card for each artifact you control, then put Braided Quipu into its owner's library third from the top.
        ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(ArtifactYouControlCount.instance), new ManaCostsImpl<>("{3}{U}")
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new BraidedQuipuEffect());
        this.getRightHalfCard().addAbility(ability.addHint(ArtifactYouControlHint.instance));

    }

    private BraidedNet(final BraidedNet card) {
        super(card);
    }

    @Override
    public BraidedNet copy() {
        return new BraidedNet(this);
    }
}

class BraidedNetEffect extends RestrictionEffect {

    BraidedNetEffect() {
        super(Duration.Custom);
        staticText = "its activated abilities can't be activated for as long as it remains tapped";
    }

    private BraidedNetEffect(final BraidedNetEffect effect) {
        super(effect);
    }

    @Override
    public BraidedNetEffect copy() {
        return new BraidedNetEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(getTargetPointer().getFirst(game, source));
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (super.isInactive(source, game)) {
            return true;
        }
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        return permanent == null || !permanent.isTapped();
    }
}

class BraidedQuipuEffect extends OneShotEffect {

    BraidedQuipuEffect() {
        super(Outcome.Benefit);
        staticText = ", then put {this} into its owner's library third from the top";
    }

    private BraidedQuipuEffect(final BraidedQuipuEffect effect) {
        super(effect);
    }

    @Override
    public BraidedQuipuEffect copy() {
        return new BraidedQuipuEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return player != null
                && permanent != null
                && player.putCardOnTopXOfLibrary(permanent, game, source, 3, true);
    }
}