package mage.cards.z;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ChooseCreatureEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZenosYaeGalvus extends TransformingDoubleFacedCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    private static final FilterPermanent filter2 = new FilterPermanent();

    static {
        filter.add(ZenosYaeGalvusPredicate.FALSE);
        filter2.add(ZenosYaeGalvusPredicate.TRUE);
    }

    public ZenosYaeGalvus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.NOBLE, SubType.WARRIOR}, "{3}{B}{B}",
                "Shinryu, Transcendent Rival",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DRAGON}, "B"
        );

        // Zenos yae Galvus
        this.getLeftHalfCard().setPT(4, 4);

        // My First Friend -- When Zenos yae Galvus enters, choose a creature an opponent controls. Until end of turn, creatures other than Zenos yae Galvus and the chosen creature get -2/-2.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new ChooseCreatureEffect(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE, false)
        );
        ability.addEffect(new BoostAllEffect(
                -2, -2, Duration.EndOfTurn, filter, true
        ).setText("until end of turn, creatures other than {this} and the chosen creature get -2/-2"));
        this.getLeftHalfCard().addAbility(ability.withFlavorWord("My First Friend"));

        // When the chosen creature leaves the battlefield, transform Zenos yae Galvus.
        this.getLeftHalfCard().addAbility(new LeavesBattlefieldAllTriggeredAbility(
                new TransformSourceEffect(), filter2
        ).setTriggerPhrase("When the chosen creature leaves the battlefield, "));

        // Shinryu, Transcendent Rival
        this.getRightHalfCard().setPT(8, 8);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // As this creature transforms into Shinryu, choose an opponent.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new ShinryuTranscendentRivalEffect()));

        // Burning Chains -- When the chosen player loses the game, you win the game.
        this.getRightHalfCard().addAbility(new ShinryuTranscendentRivalTriggeredAbility());
    }

    private ZenosYaeGalvus(final ZenosYaeGalvus card) {
        super(card);
    }

    @Override
    public ZenosYaeGalvus copy() {
        return new ZenosYaeGalvus(this);
    }
}

enum ZenosYaeGalvusPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    TRUE(true),
    FALSE(false);
    private final boolean flag;

    ZenosYaeGalvusPredicate(boolean flag) {
        this.flag = flag;
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        int zcc = game.getState().getZoneChangeCounter(input.getSource().getSourceId());
        return flag == Optional
                .of(CardUtil.getObjectZoneString(
                        "chosenCreature", input.getSource().getSourceId(), game,
                        zcc, false
                ))
                .map(game.getState()::getValue)
                .filter(MageObjectReference.class::isInstance)
                .map(MageObjectReference.class::cast)
                .map(mor -> mor.refersTo(input.getObject(), game))
                .orElse(false);
    }
}

class ShinryuTranscendentRivalEffect extends ReplacementEffectImpl {

    ShinryuTranscendentRivalEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "as this creature transforms into {this}, choose an opponent";
    }

    private ShinryuTranscendentRivalEffect(final ShinryuTranscendentRivalEffect effect) {
        super(effect);
    }

    @Override
    public ShinryuTranscendentRivalEffect copy() {
        return new ShinryuTranscendentRivalEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller == null || permanent == null) {
            return false;
        }
        TargetPlayer target = new TargetOpponent(true);
        controller.choose(Outcome.Benefit, target, source, game);
        Player opponent = game.getPlayer(target.getFirstTarget());
        if (opponent == null) {
            return false;
        }
        game.informPlayers(permanent.getName() + ": " + controller.getLogName() + " has chosen " + opponent.getLogName());
        game.getState().setValue(permanent.getId() + "_" + permanent.getZoneChangeCounter(game) + "_opponent", opponent.getId());
        permanent.addInfo("chosen opponent", CardUtil.addToolTipMarkTags("Chosen Opponent " + opponent.getLogName()), game);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TRANSFORMING;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getSourceId().equals(event.getTargetId())
                && source.getSourcePermanentIfItStillExists(game) != null;
    }
}

class ShinryuTranscendentRivalTriggeredAbility extends TriggeredAbilityImpl {

    ShinryuTranscendentRivalTriggeredAbility() {
        super(Zone.BATTLEFIELD, new WinGameSourceControllerEffect());
        this.setTriggerPhrase("When the chosen player loses the game, ");
        this.withFlavorWord("Burning Chains");
    }

    private ShinryuTranscendentRivalTriggeredAbility(final ShinryuTranscendentRivalTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ShinryuTranscendentRivalTriggeredAbility copy() {
        return new ShinryuTranscendentRivalTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int zcc = game.getState().getZoneChangeCounter(this.getSourceId());
        return Optional
                .of(this.getSourceId() + "_" + zcc + "_opponent")
                .map(game.getState()::getValue)
                .map(event.getPlayerId()::equals)
                .orElse(false);
    }
}
