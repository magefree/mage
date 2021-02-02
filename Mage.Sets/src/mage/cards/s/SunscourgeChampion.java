
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EternalizeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class SunscourgeChampion extends CardImpl {
    
    private static String rule = "Eternalize - {2}{W}{W}, Discard a card <i>({2}{W}{W}, Discard a card, Exile this card from your graveyard: Create a token that's a copy of it, except it's a 4/4 black Zombie";

    public SunscourgeChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Sunscourge Champion enters the battlefield, you gain life equal to its power.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SunscourgeChampionEffect(), false));

        // Eternalize - {2}{W}{W}, Discard a card.        
        EternalizeAbility ability = new EternalizeAbility(new ManaCostsImpl("{2}{W}{W}"), this, rule);
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private SunscourgeChampion(final SunscourgeChampion card) {
        super(card);
    }

    @Override
    public SunscourgeChampion copy() {
        return new SunscourgeChampion(this);
    }
}

class SunscourgeChampionEffect extends OneShotEffect {

    public SunscourgeChampionEffect() {
        super(Outcome.Benefit);
        staticText = "you gain life equal to its power.";
    }

    public SunscourgeChampionEffect(final SunscourgeChampionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && permanent != null && sourceObject != null) {
            controller.gainLife(permanent.getPower().getValue(), game, source);
            return true;
        }
        return false;
    }

    @Override
    public SunscourgeChampionEffect copy() {
        return new SunscourgeChampionEffect(this);
    }
}
