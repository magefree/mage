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

import static mage.cards.c.CityInABottle.getArabianNightsNamePredicates;

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

    static public List<NamePredicate> getArabianNightsNamePredicates() {
        List<NamePredicate> namePredicatesArabianNights = new ArrayList<>();
        namePredicatesArabianNights.add(new NamePredicate("Abu Ja'far"));
        namePredicatesArabianNights.add(new NamePredicate("Aladdin"));
        namePredicatesArabianNights.add(new NamePredicate("Aladdin's Lamp"));
        namePredicatesArabianNights.add(new NamePredicate("Aladdin's Ring"));
        namePredicatesArabianNights.add(new NamePredicate("Ali Baba"));
        namePredicatesArabianNights.add(new NamePredicate("Ali from Cairo"));
        namePredicatesArabianNights.add(new NamePredicate("Army of Allah"));
        namePredicatesArabianNights.add(new NamePredicate("Bazaar of Baghdad"));
        namePredicatesArabianNights.add(new NamePredicate("Bird Maiden"));
        namePredicatesArabianNights.add(new NamePredicate("Bottle of Suleiman"));
        namePredicatesArabianNights.add(new NamePredicate("Brass Man"));
        namePredicatesArabianNights.add(new NamePredicate("Camel"));
        namePredicatesArabianNights.add(new NamePredicate("City of Brass"));
        namePredicatesArabianNights.add(new NamePredicate("Cuombajj Witches"));
        namePredicatesArabianNights.add(new NamePredicate("Cyclone"));
        namePredicatesArabianNights.add(new NamePredicate("Dancing Scimitar"));
        namePredicatesArabianNights.add(new NamePredicate("Dandan"));
        namePredicatesArabianNights.add(new NamePredicate("Desert"));
        namePredicatesArabianNights.add(new NamePredicate("Desert Nomads"));
        namePredicatesArabianNights.add(new NamePredicate("Desert Twister"));
        namePredicatesArabianNights.add(new NamePredicate("Diamond Valley"));
        namePredicatesArabianNights.add(new NamePredicate("Drop of Honey"));
        namePredicatesArabianNights.add(new NamePredicate("Ebony Horse"));
        namePredicatesArabianNights.add(new NamePredicate("El-Hajjaj"));
        namePredicatesArabianNights.add(new NamePredicate("Elephant Graveyard"));
        namePredicatesArabianNights.add(new NamePredicate("Erg Raiders"));
        namePredicatesArabianNights.add(new NamePredicate("Erhnam Djinn"));
        namePredicatesArabianNights.add(new NamePredicate("Eye for an Eye"));
        namePredicatesArabianNights.add(new NamePredicate("Fishliver Oil"));
        namePredicatesArabianNights.add(new NamePredicate("Flying Carpet"));
        namePredicatesArabianNights.add(new NamePredicate("Flying Men"));
        namePredicatesArabianNights.add(new NamePredicate("Ghazban Ogre"));
        namePredicatesArabianNights.add(new NamePredicate("Giant Tortoise"));
        namePredicatesArabianNights.add(new NamePredicate("Guardian Beast"));
        namePredicatesArabianNights.add(new NamePredicate("Hasran Ogress"));
        namePredicatesArabianNights.add(new NamePredicate("Hurr Jackal"));
        namePredicatesArabianNights.add(new NamePredicate("Ifh-Biff Efreet"));
        namePredicatesArabianNights.add(new NamePredicate("Island Fish Jasconius"));
        namePredicatesArabianNights.add(new NamePredicate("Island of Wak-Wak"));
        namePredicatesArabianNights.add(new NamePredicate("Jandor's Ring"));
        namePredicatesArabianNights.add(new NamePredicate("Jandor's Saddlebags"));
        namePredicatesArabianNights.add(new NamePredicate("Jeweled Bird"));
        namePredicatesArabianNights.add(new NamePredicate("Jihad"));
        namePredicatesArabianNights.add(new NamePredicate("Junun Efreet"));
        namePredicatesArabianNights.add(new NamePredicate("Juzam Djinn"));
        namePredicatesArabianNights.add(new NamePredicate("Khabal Ghoul"));
        namePredicatesArabianNights.add(new NamePredicate("King Suleiman"));
        namePredicatesArabianNights.add(new NamePredicate("Kird Ape"));
        namePredicatesArabianNights.add(new NamePredicate("Library of Alexandria"));
        namePredicatesArabianNights.add(new NamePredicate("Magnetic Mountain"));
        namePredicatesArabianNights.add(new NamePredicate("Merchant Ship"));
        namePredicatesArabianNights.add(new NamePredicate("Metamorphosis"));
        namePredicatesArabianNights.add(new NamePredicate("Mijae Djinn"));
        namePredicatesArabianNights.add(new NamePredicate("Moorish Cavalry"));
        namePredicatesArabianNights.add(new NamePredicate("Nafs Asp"));
        namePredicatesArabianNights.add(new NamePredicate("Oasis"));
        namePredicatesArabianNights.add(new NamePredicate("Old Man of the Sea"));
        namePredicatesArabianNights.add(new NamePredicate("Oubliette"));
        namePredicatesArabianNights.add(new NamePredicate("Piety"));
        namePredicatesArabianNights.add(new NamePredicate("Pyramids"));
        namePredicatesArabianNights.add(new NamePredicate("Repentant Blacksmith"));
        namePredicatesArabianNights.add(new NamePredicate("Ring of Ma'ruf"));
        namePredicatesArabianNights.add(new NamePredicate("Rukh Egg"));
        namePredicatesArabianNights.add(new NamePredicate("Sandals of Abdallah"));
        namePredicatesArabianNights.add(new NamePredicate("Sandstorm"));
        namePredicatesArabianNights.add(new NamePredicate("Serendib Djinn"));
        namePredicatesArabianNights.add(new NamePredicate("Serendib Efreet"));
        namePredicatesArabianNights.add(new NamePredicate("Shahrazad"));
        namePredicatesArabianNights.add(new NamePredicate("Sindbad"));
        namePredicatesArabianNights.add(new NamePredicate("Singing Tree"));
        namePredicatesArabianNights.add(new NamePredicate("Sorceress Queen"));
        namePredicatesArabianNights.add(new NamePredicate("Stone-Throwing Devils"));
        namePredicatesArabianNights.add(new NamePredicate("Unstable Mutation"));
        namePredicatesArabianNights.add(new NamePredicate("War Elephant"));
        namePredicatesArabianNights.add(new NamePredicate("Wyluli Wolf"));
        namePredicatesArabianNights.add(new NamePredicate("Ydwen Efreet"));
        return namePredicatesArabianNights;
    }
}

class CityInABottleStateTriggeredAbility extends StateTriggeredAbility {

    private static final FilterPermanent filter = new FilterPermanent("a nontoken permanent originally printed in the Arabian Nights expansion other than City in a Bottle");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(Predicates.or(getArabianNightsNamePredicates()));

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
        filter.add(Predicates.or(getArabianNightsNamePredicates()));
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
        filter.add(Predicates.or(getArabianNightsNamePredicates()));
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
