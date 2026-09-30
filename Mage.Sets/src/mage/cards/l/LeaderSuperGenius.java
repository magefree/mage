package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.keyword.ConniveTargetEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author muz
 */
public final class LeaderSuperGenius extends CardImpl {

    public LeaderSuperGenius(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GAMMA);
        this.subtype.add(SubType.SCIENTIST);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // If a creature you control would connive, instead you draw a card, then that creature connives.
        this.addAbility(new SimpleStaticAbility(new LeaderSuperGeniusEffect()));

        // At the beginning of combat on your turn, target creature you control connives.
        Ability ability = new BeginningOfCombatTriggeredAbility(new ConniveTargetEffect());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private LeaderSuperGenius(final LeaderSuperGenius card) {
        super(card);
    }

    @Override
    public LeaderSuperGenius copy() {
        return new LeaderSuperGenius(this);
    }
}

class LeaderSuperGeniusEffect extends ReplacementEffectImpl {

    LeaderSuperGeniusEffect() {
        super(Duration.WhileOnBattlefield, Outcome.DrawCard, false);
        staticText = "If a creature you control would connive, instead you draw a card, then that creature connives";
    }

    private LeaderSuperGeniusEffect(final LeaderSuperGeniusEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(1, source, game);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CONNIVE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        return permanent != null
            && permanent.isControlledBy(source.getControllerId())
            && permanent.isCreature(game);
    }

    @Override
    public LeaderSuperGeniusEffect copy() {
        return new LeaderSuperGeniusEffect(this);
    }
}
