package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static mage.cards.c.CityInABottle.arabianNightsNamePredicatesOtherThanCityInABottle;

/**
 * @author emerald000
 */
public final class CityInABottle extends CardImpl {

    public CityInABottle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Whenever a nontoken permanent originally printed in the Arabian Nights expansion other than City in a Bottle is on the battlefield, its controller sacrifices it.
        this.addAbility(new CityInABottleStateTriggeredAbility());

        // Players can't play cards originally printed in the Arabian Nights expansion.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CityInABottleCantPlayEffect()));
    }

    private CityInABottle(final CityInABottle card) {
        super(card);
    }

    @Override
    public CityInABottle copy() {
        return new CityInABottle(this);
    }

    static public List<NamePredicate> arabianNightsNamePredicatesOtherThanCityInABottle() {
        List<NamePredicate> namePredicatesOtherThanCityInABottle = new ArrayList<>();
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Abu Ja'far"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Aladdin"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Aladdin's Lamp"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Aladdin's Ring"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Ali Baba"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Ali from Cairo"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Army of Allah"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Bazaar of Baghdad"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Bird Maiden"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Bottle of Suleiman"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Brass Man"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Camel"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("City of Brass"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Cuombajj Witches"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Cyclone"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Dancing Scimitar"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Dandan"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Desert"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Desert Nomads"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Desert Twister"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Diamond Valley"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Drop of Honey"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Ebony Horse"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Elephant Graveyard"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("El-Hajjaj"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Erg Raiders"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Erhnam Djinn"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Eye for an Eye"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Fishliver Oil"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Flying Carpet"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Flying Men"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Ghazban Ogre"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Giant Tortoise"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Guardian Beast"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Hasran Ogress"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Hurr Jackal"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Ifh-Biff Efreet"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Island Fish Jasconius"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Island of Wak-Wak"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Jandor's Ring"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Jandor's Saddlebags"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Jeweled Bird"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Jihad"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Junun Efreet"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Juzam Djinn"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Khabal Ghoul"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("King Suleiman"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Kird Ape"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Library of Alexandria"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Magnetic Mountain"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Merchant Ship"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Metamorphosis"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Mijae Djinn"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Moorish Cavalry"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Mountain"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Nafs Asp"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Oasis"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Old Man of the Sea"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Oubliette"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Piety"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Pyramids"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Repentant Blacksmith"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Ring of Ma'ruf"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Rukh Egg"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Sandals of Abdallah"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Sandstorm"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Serendib Djinn"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Serendib Efreet"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Shahrazad"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Sindbad"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Singing Tree"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Sorceress Queen"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Stone-Throwing Devils"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Unstable Mutation"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("War Elephant"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Wyluli Wolf"));
        namePredicatesOtherThanCityInABottle.add(new NamePredicate("Ydwen Efreet"));
        return namePredicatesOtherThanCityInABottle;
    }
}

class CityInABottleStateTriggeredAbility extends StateTriggeredAbility {

    private static final FilterPermanent filter = new FilterPermanent("a nontoken permanent originally printed in the Arabian Nights expansion other than City in a Bottle");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(Predicates.or(arabianNightsNamePredicatesOtherThanCityInABottle()));

    }

    CityInABottleStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CityInABottleSacrificeEffect());
    }

    CityInABottleStateTriggeredAbility(final CityInABottleStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CityInABottleStateTriggeredAbility copy() {
        return new CityInABottleStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getBattlefield().contains(filter, this.getControllerId(), this, game, 1);
    }

    @Override
    public String getRule() {
        return "Whenever one or more other nontoken permanents with a name originally printed in the <i>Arabian Nights</i> expansion are on the battlefield, their controllers sacrifice them";
    }
}

class CityInABottleSacrificeEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("a nontoken permanent originally printed in the Arabian Nights expansion other than City in a Bottle");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(Predicates.or(arabianNightsNamePredicatesOtherThanCityInABottle()));
    }

    CityInABottleSacrificeEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "its controller sacrifices it";
    }

    CityInABottleSacrificeEffect(final CityInABottleSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public CityInABottleSacrificeEffect copy() {
        return new CityInABottleSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            permanent.sacrifice(source, game);
        }
        return true;
    }
}

class CityInABottleCantPlayEffect extends ContinuousRuleModifyingEffectImpl {

    private static final FilterCard filter = new FilterCard("cards originally printed in the Arabian Nights expansion");

    static {
        filter.add(Predicates.or(arabianNightsNamePredicatesOtherThanCityInABottle()));
    }

    CityInABottleCantPlayEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Players can't play cards originally printed in the <i>Arabian Nights</i> expansion";
    }

    CityInABottleCantPlayEffect(final CityInABottleCantPlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CityInABottleCantPlayEffect copy() {
        return new CityInABottleCantPlayEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        return "You can't play cards originally printed in the Arabian Nights expansion";
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PLAY_LAND || event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(event.getSourceId());
        return filter.match(card, source.getControllerId(), source, game);
    }
}
