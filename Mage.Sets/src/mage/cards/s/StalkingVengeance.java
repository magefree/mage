package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class StalkingVengeance extends CardImpl {

    public StalkingVengeance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever another creature you control dies, it deals damage equal to its power to target player or planeswalker.
        Ability ability = new DiesCreatureTriggeredAbility(new StalkingVengeanceDamageEffect(), false, StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL, true);
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private StalkingVengeance(final StalkingVengeance card) {
        super(card);
    }

    @Override
    public StalkingVengeance copy() {
        return new StalkingVengeance(this);
    }
}

class StalkingVengeanceDamageEffect extends OneShotEffect {

    public StalkingVengeanceDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "it deals damage equal to its power to target player or planeswalker";
    }

    public StalkingVengeanceDamageEffect(final StalkingVengeanceDamageEffect effect) {
        super(effect);
    }

    @Override
    public StalkingVengeanceDamageEffect copy() {
        return new StalkingVengeanceDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = (Permanent) game.getLastKnownInformation(this.getTargetPointer().getFirst(game, source), Zone.BATTLEFIELD);
        if (creature != null) {
            game.damagePlayerOrPermanent(source.getFirstTarget(), creature.getPower().getValue(), creature.getId(), source, game, false, true);
            return true;
        }
        return false;
    }
}
