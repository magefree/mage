package mage.cards.y;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.OrnithopterToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class YotiaDeclaresWar extends CardImpl {

    public YotiaDeclaresWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        this.subtype.add(SubType.SAGA);

        // Read ahead
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_III, true);

        // I -- Create a 0/2 colorless Thopter artifact creature token with flying named Ornithopter.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new CreateTokenEffect(new OrnithopterToken()));

        // II -- Tap any number of untapped artifacts you control. When you do, Yotia Declares War deals that much damage to target creature or planeswalker.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new YotiaDeclaresWarEffect());

        // III -- Up to one target artifact you control becomes an artifact creature with base power and toughness 4/4 until end of turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                new Effects(
                        new AddCardTypeTargetEffect(Duration.EndOfTurn, CardType.ARTIFACT, CardType.CREATURE)
                                .setText("p to one target artifact you control becomes an artifact creature"),
                        new SetBasePowerToughnessTargetEffect(5, 5, Duration.EndOfTurn)
                                .setText("with base power and toughness 4/4 until end of turn")
                ), new TargetPermanent(0, 1, StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT)
        );
        this.addAbility(sagaAbility);
    }

    private YotiaDeclaresWar(final YotiaDeclaresWar card) {
        super(card);
    }

    @Override
    public YotiaDeclaresWar copy() {
        return new YotiaDeclaresWar(this);
    }
}

class YotiaDeclaresWarEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterControlledArtifactPermanent("untapped artifacts you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    YotiaDeclaresWarEffect() {
        super(Outcome.Benefit);
        staticText = "tap any number of untapped artifacts you control. " +
                "When you do, {this} deals that much damage to target creature or planeswalker";
    }

    private YotiaDeclaresWarEffect(final YotiaDeclaresWarEffect effect) {
        super(effect);
    }

    @Override
    public YotiaDeclaresWarEffect copy() {
        return new YotiaDeclaresWarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
        player.choose(outcome, target, source, game);
        List<Permanent> permanents = target
                .getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (permanents.isEmpty()) {
            return false;
        }
        for (Permanent permanent:permanents){
            permanent.tap(source, game);
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(permanents.size()), false
        );
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
