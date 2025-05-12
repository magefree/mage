package mage.cards.z;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.HeroToken;
import mage.game.permanent.token.Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZanarkandAncientMetropolis extends AdventureCard {

    public ZanarkandAncientMetropolis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, new CardType[]{CardType.SORCERY}, "", "Lasting Fayth", "{4}{G}{G}");

        this.subtype.add(SubType.TOWN);

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // Lasting Fayth
        // Create a 1/1 colorless Hero creature token. Put a +1/+1 counter on it for each land you control.
        this.getSpellCard().getSpellAbility().addEffect(new ZanarkandAncientMetropolisEffect());
        this.getSpellCard().getSpellAbility().addHint(LandsYouControlHint.instance);
        this.finalizeAdventure();
    }

    private ZanarkandAncientMetropolis(final ZanarkandAncientMetropolis card) {
        super(card);
    }

    @Override
    public ZanarkandAncientMetropolis copy() {
        return new ZanarkandAncientMetropolis(this);
    }
}

class ZanarkandAncientMetropolisEffect extends OneShotEffect {

    ZanarkandAncientMetropolisEffect() {
        super(Outcome.Benefit);
        staticText = "create a 1/1 colorless Hero creature token. Put a +1/+1 counter on it for each land you control";
    }

    private ZanarkandAncientMetropolisEffect(final ZanarkandAncientMetropolisEffect effect) {
        super(effect);
    }

    @Override
    public ZanarkandAncientMetropolisEffect copy() {
        return new ZanarkandAncientMetropolisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new HeroToken();
        token.putOntoBattlefield(1, game, source);
        int amount = LandsYouControlCount.instance.calculate(game, source, this);
        if (amount < 1) {
            return true;
        }
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent permanent = game.getPermanent(tokenId);
            if (permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(amount), source, game);
            }
        }
        return true;
    }
}
