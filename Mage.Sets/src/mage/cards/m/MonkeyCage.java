
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ApeToken;

/**
 *
 * @author LoneFox
 */
public final class MonkeyCage extends CardImpl {

    public MonkeyCage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // When a creature enters the battlefield, sacrifice Monkey Cage and create X 2/2 green Ape creature tokens, where X is that creature's converted mana cost.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new SacrificeSourceEffect(),
                StaticFilters.FILTER_PERMANENT_A_CREATURE, false, SetTargetPointer.PERMANENT, "");
        ability.addEffect(new MonkeyCageEffect());
        this.addAbility(ability);
    }

    private MonkeyCage(final MonkeyCage card) {
        super(card);
    }

    @Override
    public MonkeyCage copy() {
        return new MonkeyCage(this);
    }
}

class MonkeyCageEffect extends OneShotEffect {

    public MonkeyCageEffect() {
        super(Outcome.Benefit);
        staticText = "and create X 2/2 green Ape creature tokens, where X is that creature's mana value";
    }

    public MonkeyCageEffect(final MonkeyCageEffect effect) {
        super(effect);
    }

    @Override
    public MonkeyCageEffect copy() {
        return new MonkeyCageEffect(this);
    }

    public boolean apply(Game game, Ability source) {
        Permanent creature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (creature != null) {
            int cmc = creature.getManaValue();
            return new CreateTokenEffect(new ApeToken(), cmc).apply(game, source);
        }
        return false;
    }
}
