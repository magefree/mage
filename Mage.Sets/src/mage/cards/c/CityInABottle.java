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
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author emerald000
 */
public final class CityInABottle extends CardImpl {

    // Arabian Nights names per CR 206.3a
    static final List<NamePredicate> ARABIAN_NIGHTS_CARD_NAME_PREDICATES = new ArrayList<>();
    static {
        List<String> nameStrings = Arrays.asList("Abu Ja'far", "Aladdin", "Aladdin's Lamp", "Aladdin's Ring", "Ali Baba", "Ali from Cairo", "Army of Allah", "Bazaar of Baghdad", "Bird Maiden", "Bottle of Suleiman", "Brass Man", "Camel", "City in a Bottle", "City of Brass", "Cuombajj Witches", "Cyclone", "Dancing Scimitar", "Dandan", "Desert", "Desert Nomads", "Desert Twister", "Diamond Valley", "Drop of Honey", "Ebony Horse", "Elephant Graveyard", "El-Hajjaj", "Erg Raiders", "Erhnam Djinn", "Eye for an Eye", "Fishliver Oil", "Flying Carpet", "Flying Men", "Ghazban Ogre", "Giant Tortoise", "Guardian Beast", "Hasran Ogress", "Hurr Jackal", "Ifh-Biff Efreet", "Island Fish Jasconius", "Island of Wak-Wak", "Jandor's Ring", "Jandor's Saddlebags", "Jeweled Bird", "Jihad", "Junun Efreet", "Juzam Djinn", "Khabal Ghoul", "King Suleiman", "Kird Ape", "Library of Alexandria", "Magnetic Mountain", "Merchant Ship", "Metamorphosis", "Mijae Djinn", "Moorish Cavalry", "Nafs Asp", "Oasis", "Old Man of the Sea", "Oubliette", "Piety", "Pyramids", "Repentant Blacksmith", "Ring of Ma'ruf", "Rukh Egg", "Sandals of Abdallah", "Sandstorm", "Serendib Djinn", "Serendib Efreet", "Shahrazad", "Sindbad", "Singing Tree", "Sorceress Queen", "Stone-Throwing Devils", "Unstable Mutation", "War Elephant", "Wyluli Wolf", "Ydwen Efreet");
        for (String name : nameStrings) {
            ARABIAN_NIGHTS_CARD_NAME_PREDICATES.add(new NamePredicate(name));
        }
    }

    public CityInABottle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Whenever a nontoken permanent originally printed in the Arabian Nights expansion other than City in a Bottle is on the battlefield, its controller sacrifices it.
        this.addAbility(new CityInABottleStateTriggeredAbility());

        // Players can't play cards originally printed in the Arabian Nights expansion.
        this.addAbility(new SimpleStaticAbility(new CityInABottleCantPlayEffect()));
    }

    private CityInABottle(final CityInABottle card) {
        super(card);
    }

    @Override
    public CityInABottle copy() {
        return new CityInABottle(this);
    }

}

class CityInABottleStateTriggeredAbility extends StateTriggeredAbility {

    private static final FilterPermanent filter = new FilterPermanent("other nontoken permanents with a name originally printed in the Arabian Nights expansion");
    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(Predicates.or(CityInABottle.ARABIAN_NIGHTS_CARD_NAME_PREDICATES));
        filter.add(AnotherPredicate.instance);
    }

    CityInABottleStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CityInABottleSacrificeEffect());
        setTriggerPhrase("Whenever one or more other nontoken permanents with a name originally printed in the <i>Arabian Nights</i> expansion are on the battlefield, ");
    }

    private CityInABottleStateTriggeredAbility(final CityInABottleStateTriggeredAbility ability) {
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

}

class CityInABottleSacrificeEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("other nontoken permanents with a name originally printed in the Arabian Nights expansion");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(Predicates.or(CityInABottle.ARABIAN_NIGHTS_CARD_NAME_PREDICATES));
        filter.add(AnotherPredicate.instance);
    }

    CityInABottleSacrificeEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "their controllers sacrifice them";
    }

    private CityInABottleSacrificeEffect(final CityInABottleSacrificeEffect effect) {
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

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(Predicates.or(CityInABottle.ARABIAN_NIGHTS_CARD_NAME_PREDICATES));
    }

    CityInABottleCantPlayEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Players can't cast spells or play lands with a name originally printed in the <i>Arabian Nights</i> expansion";
    }

    private CityInABottleCantPlayEffect(final CityInABottleCantPlayEffect effect) {
        super(effect);
    }

    @Override
    public CityInABottleCantPlayEffect copy() {
        return new CityInABottleCantPlayEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        return "You can't play cards with a name originally printed in the Arabian Nights expansion";
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
