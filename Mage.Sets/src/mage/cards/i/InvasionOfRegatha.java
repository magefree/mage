package mage.cards.i;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamageTargetAndTargetEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterOpponent;
import mage.filter.common.FilterBattlePermanent;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPermanentOrPlayer;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfRegatha extends TransformingDoubleFacedCard {

    private static final FilterPermanentOrPlayer filter = new FilterPermanentOrPlayer(
            "another target battle or opponent",
            new FilterBattlePermanent(), new FilterOpponent()
    );

    static {
        filter.getPermanentFilter().add(AnotherPredicate.instance);
    }

    public InvasionOfRegatha(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{2}{R}",
                "Disciples of the Inferno",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.MONK}, "R"
        );

        // Invasion of Regatha
        this.getLeftHalfCard().setStartingDefense(5);
        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Regatha enters the battlefield, it deals 4 damage to another target battle or opponent and 1 damage to up to one target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetAndTargetEffect(4, 1));
        ability.addTarget(new TargetPermanentOrPlayer(filter).setTargetTag(1));
        ability.addTarget(new TargetCreaturePermanent(0, 1).setTargetTag(2));
        this.getLeftHalfCard().addAbility(ability);

        // Disciples of the Inferno
        this.getRightHalfCard().setPT(4, 4);

        // Prowess
        this.getRightHalfCard().addAbility(new ProwessAbility());

        // If a noncreature source you control would deal damage to a creature, battle, or opponent, it deals that much damage plus 2 instead.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new DisciplesOfTheInfernoEffect()));
    }

    private InvasionOfRegatha(final InvasionOfRegatha card) {
        super(card);
    }

    @Override
    public InvasionOfRegatha copy() {
        return new InvasionOfRegatha(this);
    }
}

class DisciplesOfTheInfernoEffect extends ReplacementEffectImpl {

    DisciplesOfTheInfernoEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        this.staticText = "if a noncreature source you control would deal damage " +
                "to a creature, battle, or opponent, it deals that much damage plus 2 instead";
    }

    private DisciplesOfTheInfernoEffect(final DisciplesOfTheInfernoEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 2));
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PERMANENT:
            case DAMAGE_PLAYER:
                return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !controller.hasOpponent(event.getTargetId(), game)
                && Optional
                .of(event.getTargetId())
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(permanent -> !permanent.isCreature(game) && !permanent.isBattle(game))
                .orElse(true)) {
            return false;
        }
        MageObject sourceObject;
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (sourcePermanent == null) {
            sourceObject = game.getObject(event.getSourceId());
        } else {
            sourceObject = sourcePermanent;
        }
        return sourceObject != null
                && !sourceObject.isCreature(game)
                && event.getAmount() > 0;
    }

    @Override
    public DisciplesOfTheInfernoEffect copy() {
        return new DisciplesOfTheInfernoEffect(this);
    }
}
