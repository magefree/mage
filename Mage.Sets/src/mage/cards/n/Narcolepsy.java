
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class Narcolepsy extends CardImpl {


    public Narcolepsy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        EnchantAbility ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // At the beginning of each upkeep, if enchanted creature is untapped, tap it.
        this.addAbility(new NarcolepsyTriggeredAbility());
    }

    private Narcolepsy(final Narcolepsy card) {
        super(card);
    }

    @Override
    public Narcolepsy copy() {
        return new Narcolepsy(this);
    }
}

class NarcolepsyTriggeredAbility extends BeginningOfUpkeepTriggeredAbility {
    
    NarcolepsyTriggeredAbility() {
        super(new NarcolepsyEffect(), TargetController.ANY, false);
    }
    
    NarcolepsyTriggeredAbility(final NarcolepsyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Permanent narcolepsy = game.getPermanent(this.getSourceId());
        if (narcolepsy != null) {
            Permanent enchanted = game.getPermanent(narcolepsy.getAttachedTo());
            if (enchanted != null && !enchanted.isTapped()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public NarcolepsyTriggeredAbility copy() {
        return new NarcolepsyTriggeredAbility(this);
    }
    
    @Override
    public String getRule() {
        return "At the beginning of each upkeep, if enchanted creature is untapped, tap it.";
    }
}

class NarcolepsyEffect extends OneShotEffect {

    NarcolepsyEffect() {
        super(Outcome.Tap);
    }

    NarcolepsyEffect(final NarcolepsyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent narcolepsy = game.getPermanent(source.getSourceId());
        if (narcolepsy != null) {
            Permanent enchanted = game.getPermanent(narcolepsy.getAttachedTo());
            if (enchanted != null) {
                enchanted.tap(source, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public NarcolepsyEffect copy() {
        return new NarcolepsyEffect(this);
    }
}
