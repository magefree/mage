package mage.cards.i;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.VanishingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class IdrisSoulOfTheTARDIS extends CardImpl {

    public IdrisSoulOfTheTARDIS(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.INCARNATION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vanishing 3
        this.addAbility(new VanishingAbility(3));

        // Imprint -- When Idris, Soul of the TARDIS enters the battlefield, exile another artifact you control until Idris leaves the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new IdrisSoulOfTheTARDISExileEffect()).setAbilityWord(AbilityWord.IMPRINT));

        // Idris has all activated and triggered abilities of the exiled card and gets +X/+X, where X is the exiled card's mana value.
        this.addAbility(new SimpleStaticAbility(new IdrisSoulOfTheTARDISGainEffect()));
    }

    private IdrisSoulOfTheTARDIS(final IdrisSoulOfTheTARDIS card) {
        super(card);
    }

    @Override
    public IdrisSoulOfTheTARDIS copy() {
        return new IdrisSoulOfTheTARDIS(this);
    }
}

class IdrisSoulOfTheTARDISExileEffect extends OneShotEffect {

    IdrisSoulOfTheTARDISExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile another artifact you control until {this} leaves the battlefield";
    }

    private IdrisSoulOfTheTARDISExileEffect(final IdrisSoulOfTheTARDISExileEffect effect) {
        super(effect);
    }

    @Override
    public IdrisSoulOfTheTARDISExileEffect copy() {
        return new IdrisSoulOfTheTARDISExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !game.getBattlefield().contains(
                StaticFilters.FILTER_CONTROLLED_ANOTHER_ARTIFACT, source, game, 1
        )) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_ARTIFACT);
        target.withNotTarget(true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        return permanent != null
                && new ExileUntilSourceLeavesEffect()
                .setTargetPointer(new FixedTarget(permanent, game))
                .apply(game, source);
    }
}

class IdrisSoulOfTheTARDISGainEffect extends ContinuousEffectImpl {

    IdrisSoulOfTheTARDISGainEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "{this} has all activated and triggered abilities of the exiled card " +
                "and gets +X/+X, where X is the exiled card's mana value";
    }

    private IdrisSoulOfTheTARDISGainEffect(final IdrisSoulOfTheTARDISGainEffect effect) {
        super(effect);
    }

    @Override
    public IdrisSoulOfTheTARDISGainEffect copy() {
        return new IdrisSoulOfTheTARDISGainEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(
                game, source.getSourceId(), game.getState().getZoneChangeCounter(source.getSourceId())
        ));
        if (permanent == null || exileZone == null || exileZone.isEmpty()) {
            return false;
        }
        switch (layer) {
            case AbilityAddingRemovingEffects_6:
                Set<Ability> abilities = exileZone
                        .getCards(game)
                        .stream()
                        .map(card -> card.getAbilities(game))
                        .flatMap(Collection::stream)
                        .filter(ability -> ability.isActivatedAbility() || ability.isTriggeredAbility())
                        .collect(Collectors.toSet());
                for (Ability ability : abilities) {
                    permanent.addAbility(ability, source.getSourceId(), game);
                }
                break;
            case PTChangingEffects_7:
                if (sublayer != SubLayer.ModifyPT_7c) {
                    break;
                }
                int boost = exileZone
                        .getCards(game)
                        .stream()
                        .mapToInt(MageObject::getManaValue)
                        .sum();
                permanent.addPower(boost);
                permanent.addToughness(boost);
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        switch (layer) {
            case AbilityAddingRemovingEffects_6:
            case PTChangingEffects_7:
                return true;
            default:
                return false;
        }
    }
}
