package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EmptyToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MechanizedProduction extends CardImpl {

    public MechanizedProduction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant artifact you control
        TargetPermanent auraTarget = new TargetControlledPermanent(new FilterControlledArtifactPermanent());
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Copy));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // At the beginning of your upkeep, create a token that's a copy of enchanted artifact. Then if you control eight or more artifacts with the same name as one another, you win the game.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new MechanizedProductionEffect(), TargetController.YOU, false));
    }

    private MechanizedProduction(final MechanizedProduction card) {
        super(card);
    }

    @Override
    public MechanizedProduction copy() {
        return new MechanizedProduction(this);
    }
}

class MechanizedProductionEffect extends OneShotEffect {

    public MechanizedProductionEffect() {
        super(Outcome.Benefit);
        this.staticText = "create a token that's a copy of enchanted artifact. Then if you control eight or more artifacts with the same name as one another, you win the game";
    }

    public MechanizedProductionEffect(final MechanizedProductionEffect effect) {
        super(effect);
    }

    @Override
    public MechanizedProductionEffect copy() {
        return new MechanizedProductionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourceObject != null && sourceObject.getAttachedTo() != null) {
            Permanent enchantedArtifact = game.getPermanentOrLKIBattlefield(sourceObject.getAttachedTo());
            if (enchantedArtifact != null) {
                EmptyToken token = new EmptyToken();
                CardUtil.copyTo(token).from(enchantedArtifact, game);
                token.putOntoBattlefield(1, game, source, source.getControllerId());
            }
            Map<String, Integer> countNames = new HashMap<>();
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterArtifactPermanent(), source.getControllerId(), game)) {
                int counter = countNames.getOrDefault(permanent.getName(), 0);
                countNames.put(permanent.getName(), counter + 1);
            }
            for (Entry<String, Integer> entry : countNames.entrySet()) {
                if (entry.getValue() > 7) {
                    Player controller = game.getPlayer(source.getControllerId());
                    if (controller != null) {
                        game.informPlayers(controller.getLogName() + " controls eight or more artifacts with the same name as one another (" + entry.getKey() + ").");
                        controller.won(game);
                        return true;
                    }
                }
            }
            return true;

        }
        return false;
    }
}
