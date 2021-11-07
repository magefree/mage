package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
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
public final class CurseOfLeeches extends CardImpl {

    public CurseOfLeeches(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        this.subtype.add(SubType.AURA);
        this.subtype.add(SubType.CURSE);
        this.secondSideCardClazz = mage.cards.l.LeechingLurker.class;

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // As this permanent transforms into Curse of Leeches, attach it to a player.
        this.addAbility(new SimpleStaticAbility(new CurseOfLeechesEffect()));

        // At the beginning of enchanted player's upkeep, they lose 1 life and you gain 1 life.
        ability = new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD, new LoseLifeTargetEffect(1).setText("they lose 1 life"),
                TargetController.ENCHANTED, false, true
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);

        // Daybound
        this.addAbility(new DayboundAbility());
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
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetPlayer target = new TargetPlayer();
        target.withChooseHint("Player to attach to").setNotTarget(true);
        controller.choose(Outcome.Detriment, target, source.getSourceId(), game);
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

    @Override
    public CurseOfLeechesEffect copy() {
        return new CurseOfLeechesEffect(this);
    }
}
