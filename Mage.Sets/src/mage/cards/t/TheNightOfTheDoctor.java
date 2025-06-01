package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.StaticFilters;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.*;
/**
 *
 * @author padfoothelix 
 */
public final class TheNightOfTheDoctor extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("legendary creature card");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public TheNightOfTheDoctor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}{W}");
        
        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after II.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_II);

	// I -- Destroy all creatures.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));

	// II -- Return target legendary creature card from your graveyard to the battlefield. Put your choice of a first strike, vigilance, or lifelink counter on it.
	sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new TheNightOfTheDoctorEffect(), new TargetCardInYourGraveyard(filter));

    this.addAbility(sagaAbility);

    }

    private TheNightOfTheDoctor(final TheNightOfTheDoctor card) {
        super(card);
    }

    @Override
    public TheNightOfTheDoctor copy() {
        return new TheNightOfTheDoctor(this);
    }
}

class TheNightOfTheDoctorEffect extends OneShotEffect {

    private static final Set<String> choices = new LinkedHashSet<>(Arrays.asList(
            "First strike", "Vigilance", "Lifelink"
    ));

    TheNightOfTheDoctorEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Return target legendary creature card from your graveyard to the battlefield. Put your choice of a first strike, vigilance, or lifelink counter on it";
    }

    private TheNightOfTheDoctorEffect(final TheNightOfTheDoctorEffect effect) {
        super(effect);
    }

    @Override
    public TheNightOfTheDoctorEffect copy() {
        return new TheNightOfTheDoctorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card == null || !controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
            return false;
        }
        Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
        if (permanent == null) {
            return false;
        }
	Choice choice = new ChoiceImpl(true);
	choice.setMessage("Choose first strike, vigilance, or lifelink counter");
	choice.setChoices(choices);
	controller.choose(outcome, choice, game);
	String chosen = choice.getChoice();
	if (chosen != null) {
	    permanent.addCounters(
		    CounterType.findByName(chosen.toLowerCase(Locale.ENGLISH)).createInstance(),
		    source.getControllerId(), source, game
	    );
	}
        return true;
    }
}
