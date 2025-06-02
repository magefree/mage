package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.util.RandomUtil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MechanizedProduction extends CardImpl {

    public MechanizedProduction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant artifact you control
        TargetPermanent auraTarget = new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Copy));
        this.addAbility(new EnchantAbility(auraTarget));

        // At the beginning of your upkeep, create a token that's a copy of enchanted artifact. Then if you control eight or more artifacts with the same name as one another, you win the game.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new MechanizedProductionEffect(), TargetController.YOU, false
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new WinGameSourceControllerEffect(), MechanizedProductionCondition.instance,
                "Then if you control eight or more artifacts with the same name as one another, you win the game"
        ));
        this.addAbility(ability);
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

    MechanizedProductionEffect() {
        super(Outcome.Benefit);
        this.staticText = "create a token that's a copy of enchanted artifact";
    }

    private MechanizedProductionEffect(final MechanizedProductionEffect effect) {
        super(effect);
    }

    @Override
    public MechanizedProductionEffect copy() {
        return new MechanizedProductionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantedArtifact = Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanentOrLKIBattlefield)
                .orElse(null);
        return enchantedArtifact != null
                && new CreateTokenCopyTargetEffect()
                .setSavedPermanent(enchantedArtifact)
                .apply(game, source);
    }
}

enum MechanizedProductionCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Set<Permanent> artifacts = new HashSet<>(game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT,
                source.getControllerId(), source, game
        ));
        while (artifacts.size() >= 8) {
            Permanent artifact = RandomUtil.randomFromCollection(artifacts);
            artifacts.remove(artifact);
            int amount = 0;
            for (Permanent permanent : artifacts) {
                if (!permanent.sharesName(artifact, game)) {
                    continue;
                }
                amount++;
                if (amount >= 8) {
                    return true;
                }
            }
        }
        return false;
    }
}
