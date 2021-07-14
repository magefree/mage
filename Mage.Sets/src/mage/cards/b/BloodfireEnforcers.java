
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class BloodfireEnforcers extends CardImpl {

    public BloodfireEnforcers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.HUMAN, SubType.MONK);
        this.power = new MageInt(5);
        this.toughness = new MageInt(2);

        // Bloodfire Enforcers has first strike and trample as long as an instant card and a sorcery card are in your graveyard.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield),
                new BloodfireEnforcersCondition(), "{this} has first strike"));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield),
                new BloodfireEnforcersCondition(), "and trample as long as an instant card and a sorcery card are in your graveyard"));
        this.addAbility(ability);
     
    }

    private BloodfireEnforcers(final BloodfireEnforcers card) {
        super(card);
    }

    @Override
    public BloodfireEnforcers copy() {
        return new BloodfireEnforcers(this);
    }
}


class BloodfireEnforcersCondition implements Condition {

    private static final FilterInstantOrSorceryCard filter = new FilterInstantOrSorceryCard();

    @Override
    public boolean apply(Game game, Ability source) {
        boolean instantFound = false;
        boolean sorceryFound = false;
        Player player = game.getPlayer(source.getControllerId());
        if  (player != null) {
            for(Card card : player.getGraveyard().getCards(game)) {
                if (card.isInstant(game)) {
                    if (sorceryFound) {
                        return true;
                    }
                    instantFound = true;                    
                } else if (card.isSorcery(game)) {
                    if (instantFound) {
                        return true;
                    }
                    sorceryFound = true;
                }
            }            
        }
        return false;
    }
}