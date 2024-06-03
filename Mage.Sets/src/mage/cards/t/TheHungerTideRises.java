package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.IzoniInsectToken;
import mage.players.Player;
import mage.target.common.TargetSacrifice;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class TheHungerTideRises extends CardImpl {

    public TheHungerTideRises(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I, II, III -- Create a 1/1 black and green Insect creature token.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_III, new CreateTokenEffect(new IzoniInsectToken()));

        // IV -- Sacrifice any number of creatures. Search your library and/or graveyard for a creature card with mana value less than or equal to the number of creatures sacrificed this way and put it onto the battlefield. If you search your library this way, shuffle.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_IV, new HungerTideSacrificeSearchEffect());
        this.addAbility(sagaAbility);
    }

    private TheHungerTideRises(final TheHungerTideRises card) {
        super(card);
    }

    @Override
    public TheHungerTideRises copy() {
        return new TheHungerTideRises(this);
    }
}

//Based on Scapeshift
class HungerTideSacrificeSearchEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent("other permanents you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    HungerTideSacrificeSearchEffect() {
        super(Outcome.Benefit);
        staticText = "Sacrifice any number of creatures. Search your library and/or graveyard for a creature card "
                + "with mana value less than or equal to the number of creatures sacrificed this way "
                + "and put it onto the battlefield. If you search your library this way, shuffle.";
    }

    private HungerTideSacrificeSearchEffect(final HungerTideSacrificeSearchEffect effect) {
        super(effect);
    }

    @Override
    public HungerTideSacrificeSearchEffect copy() {
        return new HungerTideSacrificeSearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int amount = 0;
        TargetSacrifice sacrifices = new TargetSacrifice(0, Integer.MAX_VALUE, StaticFilters.FILTER_PERMANENT_CREATURES);
        if (controller.choose(Outcome.Sacrifice, sacrifices, source, game)) {
            for (UUID uuid : sacrifices.getTargets()) {
                Permanent creature = game.getPermanent(uuid);
                if (creature != null) {
                    creature.sacrifice(source, game);
                    amount++;
                }
            }
        }
        FilterCard filter = new FilterCreatureCard();
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, amount));
        OneShotEffect searchEffect = new SearchLibraryGraveyardPutOntoBattlefieldEffect(filter);
        return searchEffect.apply(game, source);
    }
}
