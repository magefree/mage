package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlibouAncientWitness extends CardImpl {

    public AlibouAncientWitness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Other artifact creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE, true
        )));

        // Whenever one or more artifact creatures you control attack, Alibou, Ancient Witness deals X damage to any target and you scry X, where X is the number of tapped artifacts you control.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new AlibouAncientWitnessEffect(), 1,
                StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        ).setTriggerPhrase("Whenever one or more artifact creatures you control attack, ");
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability.addHint(AlibouAncientWitnessEffect.getHint()));
    }

    private AlibouAncientWitness(final AlibouAncientWitness card) {
        super(card);
    }

    @Override
    public AlibouAncientWitness copy() {
        return new AlibouAncientWitness(this);
    }
}

class AlibouAncientWitnessEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent();

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    private static final Hint hint = new ValueHint(
            "Tapped artifacts you control", new PermanentsOnBattlefieldCount(filter)
    );

    AlibouAncientWitnessEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals X damage to any target and you scry X, " +
                "where X is the number of tapped artifacts you control";
    }

    private AlibouAncientWitnessEffect(final AlibouAncientWitnessEffect effect) {
        super(effect);
    }

    @Override
    public AlibouAncientWitnessEffect copy() {
        return new AlibouAncientWitnessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = game.getBattlefield().count(filter, source.getControllerId(), source, game);
        if (xValue < 1) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.damage(xValue, source.getSourceId(), source, game);
        }
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            player.damage(xValue, source.getSourceId(), source, game);
        }
        player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.scry(xValue, source, game);
        }
        return true;
    }

    public static Hint getHint() {
        return hint;
    }
}
