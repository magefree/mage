package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CreaturesDiedThisTurnCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author grimreap124
 */
public final class SpymastersVault extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SWAMP);
    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public SpymastersVault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Spymaster's Vault enters the battlefield tapped unless you control a Swamp.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());
        // {B}, {T}: Target creature you control connives X, where X is the number of creatures that died this turn.
        Ability ability = new SimpleActivatedAbility(new SpymastersVaultEffect(), new ManaCostsImpl<>("{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability.addHint(CreaturesDiedThisTurnCount.getHint()));

    }

    private SpymastersVault(final SpymastersVault card) {
        super(card);
    }

    @Override
    public SpymastersVault copy() {
        return new SpymastersVault(this);
    }
}

class SpymastersVaultEffect extends OneShotEffect {

    SpymastersVaultEffect() {
        super(Outcome.Benefit);
        staticText = "Target creature you control connives X, where X is the number of creatures that died this turn";
    }

    private SpymastersVaultEffect(final SpymastersVaultEffect effect) {
        super(effect);
    }

    @Override
    public SpymastersVaultEffect copy() {
        return new SpymastersVaultEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int deaths = CreaturesDiedThisTurnCount.instance.calculate(game, source, this);

        if (deaths < 1) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        return ConniveSourceEffect.connive(permanent, deaths, source, game);
    }
}
