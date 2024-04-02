package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author DarkNik
 */
public final class Vault75MiddleSchool extends CardImpl {

    public Vault75MiddleSchool(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");
        
        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Exile all creatures with power 4 or greater.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new Vault75MiddleSchoolChapterIEffect());

        // II, III -- Put a +1/+1 counter on each creature you control.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_III,
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE)
        );

        this.addAbility(sagaAbility);
    }

    private Vault75MiddleSchool(final Vault75MiddleSchool card) {
        super(card);
    }

    @Override
    public Vault75MiddleSchool copy() { return new Vault75MiddleSchool(this); }

}

class Vault75MiddleSchoolChapterIEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_GREATER, 4));
    }

    public Vault75MiddleSchoolChapterIEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile all creatures with power 4 or greater";
    }

    private Vault75MiddleSchoolChapterIEffect(final mage.cards.v.Vault75MiddleSchoolChapterIEffect effect) {
        super(effect);
    }

    @Override
    public mage.cards.v.Vault75MiddleSchoolChapterIEffect copy() {
        return new mage.cards.v.Vault75MiddleSchoolChapterIEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }

        Set<Card> toExile = new LinkedHashSet<>(game.getBattlefield().getActivePermanents(filter, controller.getId(), source, game));
        if (toExile.isEmpty()) {
            return false;
        }

        controller.moveCardsToExile(toExile, source, game, true, CardUtil.getCardExileZoneId(game, source), permanent.getIdName());
        return true;

    }
}