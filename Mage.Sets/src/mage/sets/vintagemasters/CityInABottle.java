/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.vintagemasters;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author emerald000
 */
public class CityInABottle extends CardImpl {
    
    private static final FilterCard filterCard = new FilterCard("cards originally printed in the Arabian Nights expansion");

    public CityInABottle(UUID ownerId) {
        super(ownerId, 265, "City in a Bottle", Rarity.MYTHIC, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "VMA";

        // Whenever a nontoken permanent originally printed in the Arabian Nights expansion other than City in a Bottle is on the battlefield, its controller sacrifices it.
        this.addAbility(new CityInABottleStateTriggeredAbility());
        
        // Players can't play cards originally printed in the Arabian Nights expansion.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CityInABottleCantPlayEffect()));
    }

    public CityInABottle(final CityInABottle card) {
        super(card);
    }

    @Override
    public CityInABottle copy() {
        return new CityInABottle(this);
    }
}

class CityInABottleStateTriggeredAbility extends StateTriggeredAbility {
    
    private static final FilterPermanent filter = new FilterPermanent("a nontoken permanent originally printed in the Arabian Nights expansion other than City in a Bottle");
    static {
        filter.add(Predicates.not(new TokenPredicate()));
        filter.add(Predicates.or(
                new NamePredicate("Abu Ja'far"), 
                new NamePredicate("Aladdin"), 
                new NamePredicate("Aladdin's Lamp"), 
                new NamePredicate("Aladdin's Ring"), 
                new NamePredicate("Ali Baba"), 
                new NamePredicate("Ali from Cairo"), 
                new NamePredicate("Army of Allah"), 
                new NamePredicate("Bazaar of Baghdad"), 
                new NamePredicate("Bird Maiden"), 
                new NamePredicate("Bottle of Suleiman"), 
                new NamePredicate("Brass Man"), 
                new NamePredicate("Camel"), 
                new NamePredicate("City of Brass"), 
                new NamePredicate("Cuombajj Witches"), 
                new NamePredicate("Cyclone"), 
                new NamePredicate("Dancing Scimitar"), 
                new NamePredicate("Dandân"), 
                new NamePredicate("Desert"), 
                new NamePredicate("Desert Nomads"), 
                new NamePredicate("Desert Twister"), 
                new NamePredicate("Diamond Valley"), 
                new NamePredicate("Drop of Honey"), 
                new NamePredicate("Ebony Horse"), 
                new NamePredicate("El-Hajjâj"), 
                new NamePredicate("Elephant Graveyard"), 
                new NamePredicate("Erg Raiders"), 
                new NamePredicate("Erhnam Djinn"), 
                new NamePredicate("Eye for an Eye"), 
                new NamePredicate("Fishliver Oil"), 
                new NamePredicate("Flying Carpet"), 
                new NamePredicate("Flying Men"), 
                new NamePredicate("Ghazbán Ogre"), 
                new NamePredicate("Giant Tortoise"), 
                new NamePredicate("Guardian Beast"), 
                new NamePredicate("Hasran Ogress"), 
                new NamePredicate("Hurr Jackal"), 
                new NamePredicate("Ifh-Bíff Efreet"), 
                new NamePredicate("Island Fish Jasconius"), 
                new NamePredicate("Island of Wak-Wak"), 
                new NamePredicate("Jandor's Ring"), 
                new NamePredicate("Jandor's Saddlebags"), 
                new NamePredicate("Jeweled Bird"), 
                new NamePredicate("Jihad"), 
                new NamePredicate("Junún Efreet"), 
                new NamePredicate("Juzám Djinn"), 
                new NamePredicate("Khabál Ghoul"), 
                new NamePredicate("King Suleiman"), 
                new NamePredicate("Kird Ape"), 
                new NamePredicate("Library of Alexandria"), 
                new NamePredicate("Magnetic Mountain"), 
                new NamePredicate("Merchant Ship"), 
                new NamePredicate("Metamorphosis"), 
                new NamePredicate("Mijae Djinn"), 
                new NamePredicate("Moorish Cavalry"), 
                new NamePredicate("Nafs Asp"), 
                new NamePredicate("Oasis"), 
                new NamePredicate("Old Man of the Sea"), 
                new NamePredicate("Oubliette"), 
                new NamePredicate("Piety"), 
                new NamePredicate("Pyramids"), 
                new NamePredicate("Repentant Blacksmith"), 
                new NamePredicate("Ring of Ma'rûf"), 
                new NamePredicate("Rukh Egg"), 
                new NamePredicate("Sandals of Abdallah"), 
                new NamePredicate("Sandstorm"), 
                new NamePredicate("Serendib Djinn"), 
                new NamePredicate("Serendib Efreet"), 
                new NamePredicate("Shahrazad"), 
                new NamePredicate("Sindbad"), 
                new NamePredicate("Singing Tree"), 
                new NamePredicate("Sorceress Queen"), 
                new NamePredicate("Stone-Throwing Devils"), 
                new NamePredicate("Unstable Mutation"), 
                new NamePredicate("War Elephant"), 
                new NamePredicate("Wyluli Wolf"), 
                new NamePredicate("Ydwen Efreet")));
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
        return game.getBattlefield().contains(filter, this.getControllerId(), game, 1);
    }

    @Override
    public String getRule() {
        return "Whenever a nontoken permanent originally printed in the Arabian Nights expansion other than City in a Bottle is on the battlefield, its controller sacrifices it";
    }
}

class CityInABottleSacrificeEffect extends OneShotEffect {
    
    private static final FilterPermanent filter = new FilterPermanent("a nontoken permanent originally printed in the Arabian Nights expansion other than City in a Bottle");
    static {
        filter.add(Predicates.not(new TokenPredicate()));
        filter.add(Predicates.or(
                new NamePredicate("Abu Ja'far"), 
                new NamePredicate("Aladdin"), 
                new NamePredicate("Aladdin's Lamp"), 
                new NamePredicate("Aladdin's Ring"), 
                new NamePredicate("Ali Baba"), 
                new NamePredicate("Ali from Cairo"), 
                new NamePredicate("Army of Allah"), 
                new NamePredicate("Bazaar of Baghdad"), 
                new NamePredicate("Bird Maiden"), 
                new NamePredicate("Bottle of Suleiman"), 
                new NamePredicate("Brass Man"), 
                new NamePredicate("Camel"), 
                new NamePredicate("City of Brass"), 
                new NamePredicate("Cuombajj Witches"), 
                new NamePredicate("Cyclone"), 
                new NamePredicate("Dancing Scimitar"), 
                new NamePredicate("Dandân"), 
                new NamePredicate("Desert"), 
                new NamePredicate("Desert Nomads"), 
                new NamePredicate("Desert Twister"), 
                new NamePredicate("Diamond Valley"), 
                new NamePredicate("Drop of Honey"), 
                new NamePredicate("Ebony Horse"), 
                new NamePredicate("El-Hajjâj"), 
                new NamePredicate("Elephant Graveyard"), 
                new NamePredicate("Erg Raiders"), 
                new NamePredicate("Erhnam Djinn"), 
                new NamePredicate("Eye for an Eye"), 
                new NamePredicate("Fishliver Oil"), 
                new NamePredicate("Flying Carpet"), 
                new NamePredicate("Flying Men"), 
                new NamePredicate("Ghazbán Ogre"), 
                new NamePredicate("Giant Tortoise"), 
                new NamePredicate("Guardian Beast"), 
                new NamePredicate("Hasran Ogress"), 
                new NamePredicate("Hurr Jackal"), 
                new NamePredicate("Ifh-Bíff Efreet"), 
                new NamePredicate("Island Fish Jasconius"), 
                new NamePredicate("Island of Wak-Wak"), 
                new NamePredicate("Jandor's Ring"), 
                new NamePredicate("Jandor's Saddlebags"), 
                new NamePredicate("Jeweled Bird"), 
                new NamePredicate("Jihad"), 
                new NamePredicate("Junún Efreet"), 
                new NamePredicate("Juzám Djinn"), 
                new NamePredicate("Khabál Ghoul"), 
                new NamePredicate("King Suleiman"), 
                new NamePredicate("Kird Ape"), 
                new NamePredicate("Library of Alexandria"), 
                new NamePredicate("Magnetic Mountain"), 
                new NamePredicate("Merchant Ship"), 
                new NamePredicate("Metamorphosis"), 
                new NamePredicate("Mijae Djinn"), 
                new NamePredicate("Moorish Cavalry"), 
                new NamePredicate("Nafs Asp"), 
                new NamePredicate("Oasis"), 
                new NamePredicate("Old Man of the Sea"), 
                new NamePredicate("Oubliette"), 
                new NamePredicate("Piety"), 
                new NamePredicate("Pyramids"), 
                new NamePredicate("Repentant Blacksmith"), 
                new NamePredicate("Ring of Ma'rûf"), 
                new NamePredicate("Rukh Egg"), 
                new NamePredicate("Sandals of Abdallah"), 
                new NamePredicate("Sandstorm"), 
                new NamePredicate("Serendib Djinn"), 
                new NamePredicate("Serendib Efreet"), 
                new NamePredicate("Shahrazad"), 
                new NamePredicate("Sindbad"), 
                new NamePredicate("Singing Tree"), 
                new NamePredicate("Sorceress Queen"), 
                new NamePredicate("Stone-Throwing Devils"), 
                new NamePredicate("Unstable Mutation"), 
                new NamePredicate("War Elephant"), 
                new NamePredicate("Wyluli Wolf"), 
                new NamePredicate("Ydwen Efreet")));
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
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            permanent.sacrifice(source.getSourceId(), game);
        }
        return true;
    }
}

class CityInABottleCantPlayEffect extends ContinuousRuleModifyingEffectImpl {
    
    private static final FilterCard filter = new FilterCard("cards originally printed in the Arabian Nights expansion");
    static {
        filter.add(Predicates.or(
                new NamePredicate("Abu Ja'far"), 
                new NamePredicate("Aladdin"), 
                new NamePredicate("Aladdin's Lamp"), 
                new NamePredicate("Aladdin's Ring"), 
                new NamePredicate("Ali Baba"), 
                new NamePredicate("Ali from Cairo"), 
                new NamePredicate("Army of Allah"), 
                new NamePredicate("Bazaar of Baghdad"), 
                new NamePredicate("Bird Maiden"), 
                new NamePredicate("Bottle of Suleiman"), 
                new NamePredicate("Brass Man"), 
                new NamePredicate("Camel"),
                new NamePredicate("City in a Bottle"),
                new NamePredicate("City of Brass"), 
                new NamePredicate("Cuombajj Witches"), 
                new NamePredicate("Cyclone"), 
                new NamePredicate("Dancing Scimitar"), 
                new NamePredicate("Dandân"), 
                new NamePredicate("Desert"), 
                new NamePredicate("Desert Nomads"), 
                new NamePredicate("Desert Twister"), 
                new NamePredicate("Diamond Valley"), 
                new NamePredicate("Drop of Honey"), 
                new NamePredicate("Ebony Horse"), 
                new NamePredicate("El-Hajjâj"), 
                new NamePredicate("Elephant Graveyard"), 
                new NamePredicate("Erg Raiders"), 
                new NamePredicate("Erhnam Djinn"), 
                new NamePredicate("Eye for an Eye"), 
                new NamePredicate("Fishliver Oil"), 
                new NamePredicate("Flying Carpet"), 
                new NamePredicate("Flying Men"), 
                new NamePredicate("Ghazbán Ogre"), 
                new NamePredicate("Giant Tortoise"), 
                new NamePredicate("Guardian Beast"), 
                new NamePredicate("Hasran Ogress"), 
                new NamePredicate("Hurr Jackal"), 
                new NamePredicate("Ifh-Bíff Efreet"), 
                new NamePredicate("Island Fish Jasconius"), 
                new NamePredicate("Island of Wak-Wak"), 
                new NamePredicate("Jandor's Ring"), 
                new NamePredicate("Jandor's Saddlebags"), 
                new NamePredicate("Jeweled Bird"), 
                new NamePredicate("Jihad"), 
                new NamePredicate("Junún Efreet"), 
                new NamePredicate("Juzám Djinn"), 
                new NamePredicate("Khabál Ghoul"), 
                new NamePredicate("King Suleiman"), 
                new NamePredicate("Kird Ape"), 
                new NamePredicate("Library of Alexandria"), 
                new NamePredicate("Magnetic Mountain"), 
                new NamePredicate("Merchant Ship"), 
                new NamePredicate("Metamorphosis"), 
                new NamePredicate("Mijae Djinn"), 
                new NamePredicate("Moorish Cavalry"), 
                new NamePredicate("Nafs Asp"), 
                new NamePredicate("Oasis"), 
                new NamePredicate("Old Man of the Sea"), 
                new NamePredicate("Oubliette"), 
                new NamePredicate("Piety"), 
                new NamePredicate("Pyramids"), 
                new NamePredicate("Repentant Blacksmith"), 
                new NamePredicate("Ring of Ma'rûf"), 
                new NamePredicate("Rukh Egg"), 
                new NamePredicate("Sandals of Abdallah"), 
                new NamePredicate("Sandstorm"), 
                new NamePredicate("Serendib Djinn"), 
                new NamePredicate("Serendib Efreet"), 
                new NamePredicate("Shahrazad"), 
                new NamePredicate("Sindbad"), 
                new NamePredicate("Singing Tree"), 
                new NamePredicate("Sorceress Queen"), 
                new NamePredicate("Stone-Throwing Devils"), 
                new NamePredicate("Unstable Mutation"), 
                new NamePredicate("War Elephant"), 
                new NamePredicate("Wyluli Wolf"), 
                new NamePredicate("Ydwen Efreet")));
    }

    CityInABottleCantPlayEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Players can't play cards originally printed in the Arabian Nights expansion";
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
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.PLAY_LAND || event.getType() == EventType.CAST_SPELL) {
            Card card = game.getCard(event.getSourceId());
            if (card != null && filter.match(card, source.getSourceId(), source.getControllerId(), game)) {
                return true;
            }
        }
        return false;
    }
}
