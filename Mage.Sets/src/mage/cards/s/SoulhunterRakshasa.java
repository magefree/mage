package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class SoulhunterRakshasa extends CardImpl {

    public SoulhunterRakshasa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.CAT, SubType.DEMON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Soulhunter Rakshasa can’t block.
        this.addAbility(new CantBlockAbility());

        // When Soulhunter Rakshasa enters the battlefield, if you cast it from your hand, it deals 1 damage to target opponent for each Swamp you control.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new SoulhunterRakshasaEffect()),
                CastFromEverywhereSourceCondition.instance,
                "When {this} enters the battlefield, if you cast it from your hand, it deals 1 damage to target opponent for each Swamp you control.");
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability, new CastFromHandWatcher());
    }

    private SoulhunterRakshasa(final SoulhunterRakshasa card) {
        super(card);
    }

    @Override
    public SoulhunterRakshasa copy() {
        return new SoulhunterRakshasa(this);
    }
}

class SoulhunterRakshasaEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SWAMP);

    public SoulhunterRakshasaEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 1 damage to target opponent for each Swamp you control.";
    }

    public SoulhunterRakshasaEffect(final SoulhunterRakshasaEffect effect) {
        super(effect);
    }

    @Override
    public SoulhunterRakshasaEffect copy() {
        return new SoulhunterRakshasaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = game.getBattlefield().count(filter, source.getControllerId(), source, game);
        if (amount > 0) {
            Player player = game.getPlayer(source.getFirstTarget());
            if (player != null) {
                player.damage(amount, source.getSourceId(), source, game);
                return true;
            }
        }
        return false;
    }
}