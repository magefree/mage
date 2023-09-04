package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.watchers.common.SpellsCastWatcher;

import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SilburlindSnapper extends CardImpl {

    public SilburlindSnapper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Silburlind Snapper can't attack unless you've cast a noncreature spell this turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SilburlindSnapperEffect()));
    }

    private SilburlindSnapper(final SilburlindSnapper card) {
        super(card);
    }

    @Override
    public SilburlindSnapper copy() {
        return new SilburlindSnapper(this);
    }
}

class SilburlindSnapperEffect extends RestrictionEffect {

    public SilburlindSnapperEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless you've cast a noncreature spell this turn";
    }

    private SilburlindSnapperEffect(final SilburlindSnapperEffect effect) {
        super(effect);
    }

    @Override
    public SilburlindSnapperEffect copy() {
        return new SilburlindSnapperEffect(this);
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
            if (watcher != null) {
                List<Spell> spellsCast = watcher.getSpellsCastThisTurn(source.getControllerId());
                if (spellsCast != null) {
                    for (Spell spell : spellsCast) {
                        if (!spell.isCreature(game)) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
