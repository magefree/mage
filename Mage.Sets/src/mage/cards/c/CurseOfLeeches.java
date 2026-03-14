package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CurseOfLeeches extends TransformingDoubleFacedCard {

    public CurseOfLeeches(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA, SubType.CURSE}, "{2}{B}",
                "Leeching Lurker",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.LEECH, SubType.HORROR}, "B");

        // Curse of Leeches
        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getLeftHalfCard().getSpellAbility().addTarget(auraTarget);
        this.getLeftHalfCard().getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.getLeftHalfCard().addAbility(ability);

        // As this permanent transforms into Curse of Leeches, attach it to a player.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new CurseOfLeechesEffect()));

        // At the beginning of enchanted player's upkeep, they lose 1 life and you gain 1 life.
        ability = new BeginningOfUpkeepTriggeredAbility(
                TargetController.ENCHANTED, new LoseLifeTargetEffect(1).setText("they lose 1 life"),
                false
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.getLeftHalfCard().addAbility(ability);

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Leeching Lurker
        this.getRightHalfCard().setPT(4, 4);

        // Lifelink
        this.getRightHalfCard().addAbility(LifelinkAbility.getInstance());

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private CurseOfLeeches(final CurseOfLeeches card) {
        super(card);
    }

    @Override
    public CurseOfLeeches copy() {
        return new CurseOfLeeches(this);
    }
}

class CurseOfLeechesEffect extends ReplacementEffectImpl {

    CurseOfLeechesEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "as this permanent transforms into {this}, attach it to a player";
    }

    private CurseOfLeechesEffect(final CurseOfLeechesEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfLeechesEffect copy() {
        return new CurseOfLeechesEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetPlayer target = new TargetPlayer();
        target.withChooseHint("Player to attach to").withNotTarget(true);
        controller.choose(Outcome.Detriment, target, source, game);
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        permanent.addAttachment(target.getFirstTarget(), source, game);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TRANSFORMING;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getSourceId().equals(event.getTargetId());
    }
}
