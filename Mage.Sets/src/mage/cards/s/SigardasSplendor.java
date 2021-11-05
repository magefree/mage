package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SigardasSplendor extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a white spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public SigardasSplendor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // As Sigarda's Splendor enters the battlefield, note your life total.
        this.addAbility(new AsEntersBattlefieldAbility(new SigardasSplendorNoteEffect()));

        // At the beginning of your upkeep, draw a card if your life total is greater than or equal to the last noted life total for Sigarda's Splendor. Then note your life total.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new SigardasSplendorDrawEffect(), TargetController.YOU, false
        ).addHint(SigardasSplendorHint.instance));

        // Whenever you cast a white spell, you gain 1 life.
        this.addAbility(new SpellCastControllerTriggeredAbility(new GainLifeEffect(1), filter, false));
    }

    private SigardasSplendor(final SigardasSplendor card) {
        super(card);
    }

    @Override
    public SigardasSplendor copy() {
        return new SigardasSplendor(this);
    }

    static String getKey(Ability source, int offset) {
        return "SigardasSplendor_" + source.getControllerId() + "_" + source.getSourceId()
                + "_" + (source.getSourceObjectZoneChangeCounter() + offset);
    }
}

enum SigardasSplendorHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        if (ability.getSourcePermanentIfItStillExists(game) == null) {
            return null;
        }
        Object object = game.getState().getValue(SigardasSplendor.getKey(ability, 0));
        return "Last noted life total: " + (object != null ? (Integer) object : "None");
    }

    @Override
    public SigardasSplendorHint copy() {
        return this;
    }
}

class SigardasSplendorNoteEffect extends OneShotEffect {

    SigardasSplendorNoteEffect() {
        super(Outcome.Benefit);
        staticText = "note your life total";
    }

    private SigardasSplendorNoteEffect(final SigardasSplendorNoteEffect effect) {
        super(effect);
    }

    @Override
    public SigardasSplendorNoteEffect copy() {
        return new SigardasSplendorNoteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        game.getState().setValue(SigardasSplendor.getKey(source, -1), player.getLife());
        return true;
    }
}

class SigardasSplendorDrawEffect extends OneShotEffect {

    SigardasSplendorDrawEffect() {
        super(Outcome.Benefit);
        staticText = "draw a card if your life total is greater than or equal " +
                "to the last noted life total for {this}. Then note your life total";
    }

    private SigardasSplendorDrawEffect(final SigardasSplendorDrawEffect effect) {
        super(effect);
    }

    @Override
    public SigardasSplendorDrawEffect copy() {
        return new SigardasSplendorDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        String key = SigardasSplendor.getKey(source, 0);
        Object object = game.getState().getValue(key);
        int notedLife = object instanceof Integer ? (Integer) object : Integer.MIN_VALUE;
        if (player.getLife() >= notedLife) {
            player.drawCards(1, source, game);
        }
        game.getState().setValue(key, player.getLife());
        return true;
    }
}
