package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class SokratesAthenianTeacher extends CardImpl {

    public SokratesAthenianTeacher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Sokrates, Athenian Teacher has hexproof as long as it's untapped.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(
                        HexproofAbility.getInstance(),
                        Duration.WhileOnBattlefield
                ), SourceTappedCondition.UNTAPPED,
                "{this} has hexproof as long as it's untapped"
        )));

        // Sokratic Dialogue -- {T}: Until end of turn, target creature gains "If this creature would deal combat damage to a player, prevent that damage. This creature's controller and that player each draw half that many cards, rounded down."
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(new SimpleStaticAbility(new SokratesAthenianTeacherEffect()), Duration.EndOfTurn)
                        .setText("until end of turn, target creature gains \"" + SokratesAthenianTeacherEffect.rule + "\""),
                new TapSourceCost()
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.withFlavorWord("Sokratic Dialogue"));
    }

    private SokratesAthenianTeacher(final SokratesAthenianTeacher card) {
        super(card);
    }

    @Override
    public SokratesAthenianTeacher copy() {
        return new SokratesAthenianTeacher(this);
    }
}

class SokratesAthenianTeacherEffect extends PreventionEffectImpl {

    static final String rule = "If this creature would deal combat damage to a player, prevent that damage. " +
            "This creature's controller and that player each draw half that many cards, rounded down.";

    SokratesAthenianTeacherEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, true, false);
        staticText = rule;
    }

    private SokratesAthenianTeacherEffect(final SokratesAthenianTeacherEffect effect) {
        super(effect);
    }

    @Override
    public SokratesAthenianTeacherEffect copy() {
        return new SokratesAthenianTeacherEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game) && event.getSourceId().equals(source.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int amount = event.getAmount() / 2;
        preventDamageAction(event, source, game);
        Permanent creature = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (creature != null) {
            Player controller = game.getPlayer(creature.getControllerId());
            if (controller != null) {
                controller.drawCards(amount, source, game);
            }
        }
        Player damagedPlayer = game.getPlayer(event.getTargetId());
        if (damagedPlayer != null) {
            damagedPlayer.drawCards(amount, source, game);
        }
        return false;
    }
}
