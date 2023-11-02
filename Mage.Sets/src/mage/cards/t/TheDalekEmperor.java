package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AffinityEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.FaceVillainousChoice;
import mage.choices.VillainousChoice;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DalekToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheDalekEmperor extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.DALEK, "Daleks");
    private static final Hint hint = new ValueHint("Daleks you control", new PermanentsOnBattlefieldCount(filter));

    public TheDalekEmperor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DALEK);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Affinity for Daleks
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new AffinityEffect(filter)).addHint(hint));

        // Other Daleks you control have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        )));

        // At the beginning of combat on your turn, each opponent faces a villainous choice -- That player sacrifices a creature they control, or you create a 3/3 black Dalek artifact creature token with menace.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new TheDalekEmperorEffect(), TargetController.YOU, false
        ));
    }

    private TheDalekEmperor(final TheDalekEmperor card) {
        super(card);
    }

    @Override
    public TheDalekEmperor copy() {
        return new TheDalekEmperor(this);
    }
}

class TheDalekEmperorEffect extends OneShotEffect {

    private static final FaceVillainousChoice choice = new FaceVillainousChoice(
            Outcome.Sacrifice, new TheDalekEmperorFirstChoice(), new TheDalekEmperorSecondChoice()
    );

    TheDalekEmperorEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent " + choice.generateRule();
    }

    private TheDalekEmperorEffect(final TheDalekEmperorEffect effect) {
        super(effect);
    }

    @Override
    public TheDalekEmperorEffect copy() {
        return new TheDalekEmperorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            choice.faceChoice(player, game, source);
        }
        return true;
    }
}

class TheDalekEmperorFirstChoice extends VillainousChoice {

    TheDalekEmperorFirstChoice() {
        super("That player sacrifices a creature they control", "You sacrifice a creature");
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        if (!game.getBattlefield().contains(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                player.getId(), source, game, 1
        )) {
            return false;
        }
        TargetPermanent target = new TargetControlledCreaturePermanent();
        target.withNotTarget(true);
        player.choose(Outcome.Sacrifice, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        return permanent != null && permanent.sacrifice(source, game);
    }
}

class TheDalekEmperorSecondChoice extends VillainousChoice {

    TheDalekEmperorSecondChoice() {
        super("you create a 3/3 black Dalek artifact creature token with menace", "{controller} creates a Dalek token");
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        return new DalekToken().putOntoBattlefield(1, game, source);
    }
}
