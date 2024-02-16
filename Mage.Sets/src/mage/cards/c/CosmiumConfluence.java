package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.custom.CreatureToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetEnchantmentPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CosmiumConfluence extends CardImpl {

    private static final FilterCard filter = new FilterCard("Cave card");

    static {
        filter.add(SubType.CAVE.getPredicate());
    }

    public CosmiumConfluence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        // Choose three. You may choose the same mode more than once.
        this.getSpellAbility().getModes().setMinModes(3);
        this.getSpellAbility().getModes().setMaxModes(3);
        this.getSpellAbility().getModes().setMayChooseSameModeMoreThanOnce(true);

        // * Search your library for a Cave card, put it onto the battlefield tapped, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(filter), true
        ));

        // * Put three +1/+1 counters on a Cave you control. It becomes a 0/0 Elemental creature with haste. It's still a land.
        this.getSpellAbility().addMode(new Mode(new CosmiumConfluenceEffect()));

        // * Destroy target enchantment.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetEnchantmentPermanent());
        this.getSpellAbility().addMode(mode);
    }

    private CosmiumConfluence(final CosmiumConfluence card) {
        super(card);
    }

    @Override
    public CosmiumConfluence copy() {
        return new CosmiumConfluence(this);
    }
}

class CosmiumConfluenceEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.CAVE, "Cave you control");

    CosmiumConfluenceEffect() {
        super(Outcome.BecomeCreature);
        staticText = "put three +1/+1 counters on a Cave you control. "
                + "It becomes a 0/0 Elemental creature with haste. It's still a land";
    }

    private CosmiumConfluenceEffect(final CosmiumConfluenceEffect effect) {
        super(effect);
    }

    @Override
    public CosmiumConfluenceEffect copy() {
        return new CosmiumConfluenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        TargetPermanent target = new TargetPermanent(filter);
        if (!target.canChoose(controller.getId(), source, game)) {
            return false;
        }

        controller.choose(outcome, target, source, game);
        FixedTarget fixedTarget = new FixedTarget(target.getFirstTarget(), game);
        new AddCountersTargetEffect(CounterType.P1P1.createInstance(3))
                .setTargetPointer(fixedTarget)
                .apply(game, source);
        ContinuousEffect effect = new BecomesCreatureTargetEffect(
                new CreatureToken(0, 0, "0/0 Elemental creature with haste", SubType.ELEMENTAL)
                        .withAbility(HasteAbility.getInstance()),
                false, true, Duration.Custom
        );
        effect.setTargetPointer(fixedTarget);
        game.addEffect(effect, source);
        return true;
    }

}